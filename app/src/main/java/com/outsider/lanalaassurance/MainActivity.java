package com.outsider.lanalaassurance;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.outsider.lanalaassurance.Adapter.MainSliderAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QR_SCAN = 101;
    private TextView startScanner;
    private TextView sinscrir;
    private TextView mdpoublier;
    private TextView avecemail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        sinscrir = findViewById(R.id.sinscrir);
        mdpoublier = findViewById(R.id.mdpoublier);
        avecemail = findViewById(R.id.avecemail);
        startScanner = findViewById(R.id.startscanner);
        startScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                }else{
                    Intent i = new Intent(MainActivity.this,QrCodeActivity.class);
                    startActivityForResult( i,REQUEST_CODE_QR_SCAN);

                }
            }
        });

        sinscrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        avecemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText input = new EditText(MainActivity.this);
                input.setHint("Mot de passe");
                input.setTransformationMethod(PasswordTransformationMethod.getInstance());

                final EditText inputemail = new EditText(MainActivity.this);
                inputemail.setHint("Email");
                inputemail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                layout.addView(inputemail);
                layout.addView(input);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Connexion");
                alertDialog.setMessage("Veuillez saisir votre email et mot de passe !");
                alertDialog.setView(layout);
                alertDialog.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        progressDialog.setMessage("S'il vous plaît, attendez..");
                        progressDialog.setTitle("Chargement");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        loginuser(inputemail.getText().toString(), input.getText().toString());

                    }
                });
                alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();

            }
        });

        mdpoublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(MainActivity.this);
                input.setHint("Email");
                input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Mot de passe oublié");
                alertDialog.setMessage("Veuillez entrer l'adresse e-mail associée à votre compte.\n" +
                        "Les instructions pour réinitialiser le mot de passe seront transmises à cette adresse e-mail.");
                alertDialog.setView(input);
                alertDialog.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.setMessage("S'il vous plaît, attendez..");
                        progressDialog.setTitle("Chargement");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        passoOublier(input.getText().toString());
                    }
                });
                alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void passoOublier(String email) {
        Ion.with(MainActivity.this)
                .load("https://edi.proassur.tn/api.proassur_vie/api/Account/SendPassword/")
                .setBodyParameter("email", email)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        progressDialog.dismiss();
                        if(result != null){
                            Toast.makeText(MainActivity.this, result.get("message").toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_QR_SCAN) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this,QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_QR_SCAN);

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK)
        {
            Log.d("d","COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            //Getting the passed result

            final EditText input = new EditText(MainActivity.this);
            input.setHint("Mot de passe");
            input.setTransformationMethod(PasswordTransformationMethod.getInstance());

            final String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            //Log.d("d","************* "+result+" ***********");

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Mot de passe");
            alertDialog.setMessage("Veuillez saisir votre mot de passe !");
            alertDialog.setView(input);
            alertDialog.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    progressDialog.setMessage("S'il vous plaît, attendez..");
                    progressDialog.setTitle("Chargement");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    loginuser(result, input.getText().toString());
                }
            });
            alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();

        }
    }

    public void loginuser(String email, String password){

//        JsonObject json = new JsonObject();
//        json.addProperty("email", "Youssouf@gmail.com");
//        json.addProperty("password", "1234");

        Ion.with(MainActivity.this)
                .load("https://edi.proassur.tn/api.proassur_vie/api/wsassur/getUserByEmailAndPassword?email="+email+"&password="+password)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if(result != null){
                            //Toast.makeText(MainActivity.this, result.get("code").toString(), Toast.LENGTH_SHORT).show();
                            Log.d("d","************* "+result.get("message")+" ***********");
                            if(result.get("code").toString().equals("200")){

                                String session = result.get("id").getAsString();
                                Log.d("idedede  ", session);
                                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                                editor.putString("id", session);
                                editor.putString("codeclient", result.get("code_client").getAsString());
                                editor.putString("email", result.get("email").getAsString());
                                editor.putString("tel", result.get("TEL").getAsString());
                                editor.putString("nom", result.get("Nom").getAsString());
                                editor.apply();

                                progressDialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                startActivity(intent);
                                finish();

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, result.get("message").toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}

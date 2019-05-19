package com.outsider.lanalaassurance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ProfilActivity extends AppCompatActivity {

    TextView nomtv, teltv, emailtv;
    ImageView changepwd;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        progressDialog = new ProgressDialog(this);
        nomtv = findViewById(R.id.nomID);
        teltv = findViewById(R.id.mobileID);
        emailtv = findViewById(R.id.emailID);
        changepwd = findViewById(R.id.changepassID);

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        String email = prefs.getString("email", "");
        String nom = prefs.getString("nom", "");
        String tel = prefs.getString("tel", "");

        nomtv.setText(nom);
        teltv.setText(tel);
        emailtv.setText(email);

        changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(ProfilActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText input = new EditText(ProfilActivity.this);
                input.setHint("Mot de passe actuel");
                input.setTransformationMethod(PasswordTransformationMethod.getInstance());

                final EditText ninput = new EditText(ProfilActivity.this);
                ninput.setHint("Nouveau mot de passe");
                ninput.setTransformationMethod(PasswordTransformationMethod.getInstance());

                final EditText cninput = new EditText(ProfilActivity.this);
                cninput.setHint("Confirmer le nouveau mot de passe");
                cninput.setTransformationMethod(PasswordTransformationMethod.getInstance());

                layout.addView(input);
                layout.addView(ninput);
                layout.addView(cninput);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfilActivity.this);
                alertDialog.setTitle("Modifier le mot de passe");
                //alertDialog.setMessage("Veuillez saisir votre email et mot de passe !");
                alertDialog.setView(layout);
                alertDialog.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        progressDialog.setMessage("S'il vous plaît, attendez..");
                        progressDialog.setTitle("Chargement");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        modifierPassword(input.getText().toString(), ninput.getText().toString(), cninput.getText().toString());

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

    private void modifierPassword(String currentPass, String newPass, String confirmPass) {
        if(!newPass.equals(confirmPass)){
            progressDialog.dismiss();
            Toast.makeText(ProfilActivity.this, "Le nouveau mot de passe et le mot de passe de confirmation doivent être identiques", Toast.LENGTH_LONG).show();
        }else {

            SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
            String id = prefs.getString("id", null);
            String codeclient = prefs.getString("codeclient", null);
            String email = prefs.getString("email", null);

            Log.d("iddddddd------ ", id);
            Log.d("Code Client ------ ", codeclient);
            Log.d("Emaill Client ------ ", email);

            JsonObject json = new JsonObject();
            json.addProperty("code_prestataire", codeclient);
            json.addProperty("sid", id);
            json.addProperty("username", email);
            json.addProperty("ancien_password", currentPass);
            json.addProperty("new_password", newPass);
            json.addProperty("confirmpassword", confirmPass);

            Ion.with(ProfilActivity.this)
                    .load("https://edi.proassur.tn/api.proassur_vie/api/Account/ChangerPassword/")
                    .setBodyParameter("code_prestataire", codeclient)
                    .setBodyParameter("sid", id)
                    .setBodyParameter("username", email)
                    .setBodyParameter("ancien_password", currentPass)
                    .setBodyParameter("new_password", newPass)
                    .setBodyParameter("confirmpassword", confirmPass)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            progressDialog.dismiss();
                            if(result != null){
                                Toast.makeText(ProfilActivity.this, result.get("message").toString(), Toast.LENGTH_LONG).show();
                                if(result.get("code").toString().equals("200")){
                                    Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }
                            }
                        }
                    });
        }
    }
}

//if(input.getText().toString().isEmpty()){
//        input.setError("*");
//        }else if(ninput.getText().toString().isEmpty()){
//        ninput.setError("*");
//        }else if(cninput.getText().toString().isEmpty()){
//        cninput.setError("*");
//        }else if (!ninput.getText().toString().equals(cninput.getText().toString())){
//        cninput.setError("!");
//        ninput.setError("!");
//        Toast.makeText(ProfilActivity.this, "Le nouveau mot de passe et le mot de passe de confirmation doivent être identiques", Toast.LENGTH_SHORT).show();
//        }else{
//
//        }
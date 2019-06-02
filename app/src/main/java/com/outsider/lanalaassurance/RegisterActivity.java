package com.outsider.lanalaassurance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.outsider.lanalaassurance.Adapter.ImpayeeAdapter;

public class RegisterActivity extends AppCompatActivity {


    EditText nomprenom, cin, mobile, email, postal, ville, adresse;
    Button submit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("S'il vous plaît, attendez..");
        progressDialog.setTitle("Chargement");
        progressDialog.setCancelable(false);

        nomprenom = findViewById(R.id.prenomRegister);
        cin = findViewById(R.id.cinRgister);
        mobile = findViewById(R.id.mobileRgister);
        email = findViewById(R.id.emailRegister);
        postal = findViewById(R.id.postalRegister);
        ville = findViewById(R.id.villeRegister);
        adresse = findViewById(R.id.addressRegister);

        submit = findViewById(R.id.registerBtn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                String data = "{\"CODE_AGENCE\":\"1\"," +
                        "\"CODE_UTILISATEUR\":\"0\"," +
                        "\"NOM_PRENOM\":\""+nomprenom.getText().toString()+"\"," +
                        "\"ADRESSE\":\""+adresse.getText().toString()+"\"," +
                        "\"VILLE\":\""+ville.getText().toString()+"\"," +
                        "\"CODE_POSTAL\":\""+postal.getText().toString()+"\"," +
                        "\"TYPE_PERSONNE\":\"PERS\"," +
                        "\"TYPE_IDENTIFIANT\":{" +
                        "\"CODE_TYPE\":1," +
                        "\"VALEUR_IDENTIFIANT\":\""+cin.getText().toString()+"\"}," +
                        "\"CARACTERISTIQUES\":[{" +
                        "\"ID\":35," +
                        "\"VALEUR_CARACT\":\"Tunisia\"}," +
                        "{\"ID\":41," +
                        "\"VALEUR_CARACT\":\""+mobile.getText().toString()+"\"}," +
                        "{\"ID\":42," +
                        "\"VALEUR_CARACT\":\"78945612\"}," +
                        "{\"ID\":43," +
                        "\"VALEUR_CARACT\":\""+email.getText().toString()+"\"}]}";

                Ion.with(RegisterActivity.this)
                        .load("https://edi.proassur.tn/api.proassur_vie/api/personne/SaveDataPerson")
                        .setBodyParameter("DataToSave", data)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                progressDialog.dismiss();
                                Log.d("d","************* "+result+" ***********");
                                if(result != null){

                                    Log.d("d","************* "+result.get("message")+" ***********");

                                    if(result.get("code").toString().equals("200")){

                                        Toast.makeText(RegisterActivity.this, "Votre compte est en attente de validation," +
                                                "Veuillez vérifier votre email.", Toast.LENGTH_LONG).show();
                                        finish();
                                    }else if(result.get("code").toString().equals("502")){
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }else{
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, result.get("message").toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
            }
        });
    }
}

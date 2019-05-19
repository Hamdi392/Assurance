package com.outsider.lanalaassurance.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.outsider.lanalaassurance.Main2Activity;
import com.outsider.lanalaassurance.MainActivity;
import com.outsider.lanalaassurance.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private ProgressDialog progressDialog;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("S'il vous plaît, attendez..");
        progressDialog.setTitle("Chargement");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String num = getArguments().getString("num");
        String nom = getArguments().getString("nom");
        String date = getArguments().getString("date");
        String deces = getArguments().getString("casdeces");
        String etatt = getArguments().getString("etat");
        String vie = getArguments().getString("casvie");
        String id = getArguments().getString("id");
        String codeclient = getArguments().getString("codeclient");

        TextView textView = view.findViewById(R.id.numPoliceD);
        TextView nomtv = view.findViewById(R.id.nomPoliceD);
        TextView datetv = view.findViewById(R.id.datePoliceD);
        TextView vietv = view.findViewById(R.id.viePoliceD);
        TextView decestv = view.findViewById(R.id.decesPoliceD);
        TextView etat = view.findViewById(R.id.etatPoliceDl);
        final TextView capitall = view.findViewById(R.id.capitalc);
        final TextView epargne = view.findViewById(R.id.monteparge);
        final TextView risquee = view.findViewById(R.id.montrisque);
        final TextView periodee = view.findViewById(R.id.periodic);
        final TextView mode = view.findViewById(R.id.modepay);


        Ion.with(getActivity())
                .load("https://edi.proassur.tn/api.proassur_vie/api/contrat/GetDataContratEpargne?codeSession="+id+"&numero_police="+num+"&code_client="+codeclient)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if(result != null){
                            //Toast.makeText(MainActivity.this, result.get("code").toString(), Toast.LENGTH_SHORT).show();
                            Log.d("d","************* "+result.get("message")+" ***********");
                            if(result.get("code").toString().equals("200")){

                                String modev = result.get("ModeVersement").getAsString();
                                String cumul = result.get("CumuleVersementEpargne").getAsString();
                                String capital = result.get("capitalConstitue").getAsString();
                                String risque = result.get("MontantDeRisque").getAsString();
                                String periode = result.get("Periodicite").getAsString();

                                mode.setText(modev);
                                epargne.setText(cumul);
                                capitall.setText(capital);
                                risquee.setText(risque);
                                periodee.setText(periode);
                                progressDialog.dismiss();

                            }else if(result.get("code").toString().equals("502")){
                                progressDialog.dismiss();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }
                    }
                });


        etat.setText(etatt);
        textView.setText(num);
        nomtv.setText(nom);
        datetv.setText(date);
        vietv.setText(vie);
        decestv.setText(deces);

        return view;
    }

}

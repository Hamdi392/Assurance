package com.outsider.lanalaassurance.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.outsider.lanalaassurance.Adapter.ImpayeeAdapter;
import com.outsider.lanalaassurance.Adapter.ListContartAdapter;
import com.outsider.lanalaassurance.Impayee;
import com.outsider.lanalaassurance.Main2Activity;
import com.outsider.lanalaassurance.MainActivity;
import com.outsider.lanalaassurance.Police;
import com.outsider.lanalaassurance.R;
import com.outsider.lanalaassurance.WebPaymentActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ImpayeeFragment extends Fragment {


    private ProgressDialog progressDialog;
    Button btnSubmit;
    ImpayeeAdapter impayeeAdapter;

    public ImpayeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_impayee, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("S'il vous plaît, attendez..");
        progressDialog.setTitle("Chargement");
        progressDialog.setCancelable(false);
        progressDialog.show();

        btnSubmit = view.findViewById(R.id.btnpayeimapyee);
        btnSubmit.setEnabled(false);

        final String id = getArguments().getString("id");
        final String codeclient = getArguments().getString("codeclient");
        String num = getArguments().getString("num");
        final ListView listView = view.findViewById(R.id.listimpayee);
        final ArrayList<Impayee> impayeeArrayList = new ArrayList<>();

        if (id != null) {
            Ion.with(getActivity())
                    .load("https://edi.proassur.tn/api.proassur_vie/api/vie/GetListeQuittancesImpayees?UserId="+
                            codeclient+"&ContratId="+num+"&codeSession="+id)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
                            if(result != null){
                                //Toast.makeText(MainActivity.this, result.get("code").toString(), Toast.LENGTH_SHORT).show();
                                Log.d("d","************* "+result.get("message")+" ***********");
                                if(result.get("code").toString().equals("200")){

                                    Gson gson = new Gson();
                                    Impayee[] impayees = gson.fromJson(result.get("quittancesImapyees").toString(), Impayee[].class);
                                    for(Impayee impayee : impayees){
                                        impayeeArrayList.add(impayee);
                                    }

                                    progressDialog.dismiss();
                                    impayeeAdapter = new ImpayeeAdapter(getContext(), impayeeArrayList);
                                    listView.setAdapter(impayeeAdapter);
                                    btnSubmit.setEnabled(true);

                                }else if(result.get("code").toString().equals("502")){
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();

                                }else if(result.get("code").toString().equals("550")){
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Aucune quittance impayée trouvée pour ce numéro de contrat", Toast.LENGTH_LONG).show();

                                }else{
                                    progressDialog.dismiss();
                                }
                            }
                        }
                    });
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Impayee> impayees = ((ImpayeeAdapter)listView.getAdapter()).getSelectActorList();
                JsonArray jsonArray = new JsonArray();
                 for(int i=0; i<impayees.size(); i++){
                    System.out.println("-----------*-*-*-*-*-* : "+impayees.get(i).getNumero_quittance()+ "*** "+impayees.get(i).isIsselected());

                     JsonObject jsonObject = new JsonObject();
                     jsonObject.addProperty("numero_quittance", impayees.get(i).getNumero_quittance());
                     String montant = impayees.get(i).getMontant_quittance();
                     jsonObject.addProperty("montant", Integer.parseInt(montant.replaceAll("\\s+","")));

                     jsonArray.add(jsonObject);
                 }
                 payer(codeclient, id, jsonArray);
            }
        });

        return view;
    }

    private void payer(String code, String id, JsonArray list) {


        JsonObject json = new JsonObject();
        json.addProperty("code_client", code);
        json.addProperty("codeSession", id);
        json.addProperty("codeSession", id);
        json.add("ListQuittance", list);

        System.out.println(json);

        Ion.with(getActivity())
                .load("https://edi.proassur.tn/PROASSUR.LANALA/WebUI/Financier/PaiementEnLigneInMobile.aspx")
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        Log.d("d",result);
                        progressDialog.dismiss();
                        Intent intent = new Intent(getActivity(), WebPaymentActivity.class);
                        intent.putExtra("data", result);
                        startActivity(intent);

                    }
                });
    }

}

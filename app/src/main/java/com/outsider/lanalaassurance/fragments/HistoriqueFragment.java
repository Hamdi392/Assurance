package com.outsider.lanalaassurance.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.outsider.lanalaassurance.Adapter.MainSliderAdapter;
import com.outsider.lanalaassurance.Adapter.PicassoImageLoadingService;
import com.outsider.lanalaassurance.Historique;
import com.outsider.lanalaassurance.MainActivity;
import com.outsider.lanalaassurance.Police;
import com.outsider.lanalaassurance.R;

import java.util.ArrayList;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;


public class HistoriqueFragment extends Fragment {


    Slider slider;
    TextView num, datedu, dateau, montant;
    private ProgressDialog progressDialog;

    public HistoriqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View converView =  inflater.inflate(R.layout.fragment_historique, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("S'il vous plaît, attendez..");
        progressDialog.setTitle("Chargement");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Slider.init(new PicassoImageLoadingService(getContext()));
        slider = converView.findViewById(R.id.banner_slider1);
        //slider.setAdapter(new MainSliderAdapter());

        num = converView.findViewById(R.id.numerohist);
        dateau = converView.findViewById(R.id.dateauhisto);
        datedu = converView.findViewById(R.id.dateduhisto);
        montant = converView.findViewById(R.id.montanthisto);

        String numss = getArguments().getString("num");
        String id = getArguments().getString("id");
        String codeclient = getArguments().getString("codeclient");
        final ArrayList<Historique> historiqueArrayList = new ArrayList<>();

        Ion.with(getActivity())
                .load("https://edi.proassur.tn/api.proassur_vie/api/vie/GetHistoriqueVersement?codeSession="+id+"&contratId="+numss+"&UserId="+codeclient)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if(result != null){
                            //Toast.makeText(MainActivity.this, result.get("code").toString(), Toast.LENGTH_SHORT).show();
                            Log.d("d","************* "+result.get("message")+" ***********");
                            if(result.get("code").toString().equals("200")){

                                Gson gson = new Gson();
                                Historique[] historiques = gson.fromJson(result.get("ListEncaissements").toString(), Historique[].class);
                                for(Historique historique : historiques){
                                    historiqueArrayList.add(historique);
                                }

                                progressDialog.dismiss();
                                MainSliderAdapter mainSliderAdapter = new MainSliderAdapter();
                                mainSliderAdapter.nb = historiqueArrayList.size();
                                slider.setAdapter(mainSliderAdapter);

                                slider.setOnSlideClickListener(new OnSlideClickListener() {
                                    @Override
                                    public void onSlideClick(int position) {
                                        Historique historique = historiqueArrayList.get(position);
                                        num.setText(historique.getNumeroQuittance());
                                        dateau.setText(historique.getDate_au().substring(0,10));
                                        datedu.setText(historique.getDate_du().substring(0,10));
                                        montant.setText(historique.getMontant_payee());
                                    }
                                });

                            }else if(result.get("code").toString().equals("502")){
                                progressDialog.dismiss();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }else{
                                progressDialog.dismiss();
                            }
                        }
                    }
                });





        return converView;
    }

}

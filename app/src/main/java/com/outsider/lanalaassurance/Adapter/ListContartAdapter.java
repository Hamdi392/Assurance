package com.outsider.lanalaassurance.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.outsider.lanalaassurance.Contrat;
import com.outsider.lanalaassurance.Police;
import com.outsider.lanalaassurance.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListContartAdapter extends ArrayAdapter<Police> {

    private Context ctx;
    private ArrayList<Police> mydata;
    public ListContartAdapter(@NonNull Context context, ArrayList<Police> ordors) {
        super(context, R.layout.quittance_item, ordors);
        this.ctx = context;
        this.mydata = ordors;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(ctx).inflate(R.layout.quittance_item, parent, false);

        TextView datedu = convertView.findViewById(R.id.duDatePolice);
        TextView dateau = convertView.findViewById(R.id.auDatePolice);
        TextView product = convertView.findViewById(R.id.productId);
        TextView etat = convertView.findViewById(R.id.etatIdPolice);
        TextView number = convertView.findViewById(R.id.numPolice);
        TextView casvie = convertView.findViewById(R.id.casvie);
        TextView casdeces = convertView.findViewById(R.id.casdeces);

        Police police = getItem(position);

        dateau.setText(police.getDate_au().substring(0,10));
        datedu.setText(police.getDate_du().substring(0,10));
        product.setText(police.getNomProduit());
        etat.setText(police.getEtatPolice());
        number.setText(police.getNumeroPolice());
        if(police.getBeneficiairesCasDeces().length > 0){
            casdeces.setText(police.getBeneficiairesCasDeces()[0].getNomPrenom());
        }else {
            casdeces.setText("-");
        }
        if(police.getBeneficiairesCasVie().length > 0){
            casvie.setText(police.getBeneficiairesCasVie()[0].getNomPrenom());
        }else{
            casvie.setText("-");
        }


        if ((position%2)!=0){
            convertView.setBackgroundResource(R.color.mygray);
        }

        return convertView;


    }
}

package com.outsider.lanalaassurance.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.outsider.lanalaassurance.Historique;
import com.outsider.lanalaassurance.R;

import java.util.ArrayList;

public class HistoAdapter extends ArrayAdapter<Historique>{

    private Context ctx;
    private ArrayList<Historique> mydata;
    public HistoAdapter(@NonNull Context context, @NonNull ArrayList<Historique> objects) {
        super(context, R.layout.histo_item, objects);
        this.ctx = context;
        this.mydata = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        convertView = LayoutInflater.from(ctx).inflate(R.layout.histo_item,parent, false);

        TextView num = convertView.findViewById(R.id.numerohist);
        TextView datedebut = convertView.findViewById(R.id.dateduhisto);
        TextView datefin = convertView.findViewById(R.id.dateauhisto);
        TextView montant = convertView.findViewById(R.id.montanthisto);

        Historique histo = getItem(position);

        num.setText(histo.getNumeroQuittance());
        datedebut.setText(histo.getDate_du().substring(0,10));
        datefin.setText(histo.getDate_au().substring(0,10));
        montant.setText(histo.getMontant_payee());

        return convertView;
    }
}

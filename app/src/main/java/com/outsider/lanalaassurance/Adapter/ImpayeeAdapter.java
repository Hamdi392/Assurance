package com.outsider.lanalaassurance.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.outsider.lanalaassurance.Impayee;
import com.outsider.lanalaassurance.R;

import java.util.ArrayList;
import java.util.List;

public class ImpayeeAdapter extends ArrayAdapter<Impayee> {

    private Context ctx;
    private ArrayList<Impayee> mydata;
    public ImpayeeAdapter(@NonNull Context context, @NonNull ArrayList<Impayee> objects) {
        super(context, R.layout.item_impayee, objects);
        this.ctx = context;
        this.mydata = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_impayee,parent, false);

        TextView num = convertView.findViewById(R.id.contratNumberImpaye);
        TextView dateeff = convertView.findViewById(R.id.dateeffimayee);
        TextView datedebut = convertView.findViewById(R.id.duidimayee);
        TextView datefin = convertView.findViewById(R.id.audateimpayee);
        TextView montant = convertView.findViewById(R.id.montantimpaye);

        Impayee impayee = getItem(position);

        num.setText(impayee.getNumContart());
        dateeff.setText(impayee.getDateEffet());
        datedebut.setText(impayee.getDateDebut());
        datefin.setText(impayee.getDateFin());
        montant.setText(impayee.getMonatnt());

        return convertView;
    }
}

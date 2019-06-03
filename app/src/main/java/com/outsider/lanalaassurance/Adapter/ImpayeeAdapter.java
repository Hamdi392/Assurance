package com.outsider.lanalaassurance.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.outsider.lanalaassurance.Impayee;
import com.outsider.lanalaassurance.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ImpayeeAdapter extends ArrayAdapter<Impayee> {

    private Context ctx;
    private ArrayList<Impayee> mydata;
    private CheckBox checkBox;
    public ImpayeeAdapter(@NonNull Context context, @NonNull ArrayList<Impayee> objects) {
        super(context, R.layout.item_impayee, objects);
        this.ctx = context;
        this.mydata = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_impayee,parent, false);

        TextView num = convertView.findViewById(R.id.contratNumberImpaye);
        TextView dateeff = convertView.findViewById(R.id.dateeffimayee);
        TextView datedebut = convertView.findViewById(R.id.duidimayee);
        TextView datefin = convertView.findViewById(R.id.audateimpayee);
        TextView montant = convertView.findViewById(R.id.montantimpaye);
        checkBox = convertView.findViewById(R.id.checkboximpayee);

        Impayee impayee = getItem(position);
        Date du = new Date(), au = new Date(), eff = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        try {
             du=dateFormat.parse(impayee.getDate_du());
             au=dateFormat.parse(impayee.getDate_au());
             eff=dateFormat.parse(impayee.getDate_effet_quittance());

        }
        catch(Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep"+e);
        }

        num.setText(impayee.getNumero_quittance());
        dateeff.setText(dateFormat.format(au));
        datedebut.setText(dateFormat.format(du));
        datefin.setText(dateFormat.format(eff));
        montant.setText(impayee.getMontant_quittance());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSelected = checkBox.isSelected();
                mydata.get(position).setIsselected(true);
                System.out.println(mydata.get(position).isIsselected());
            }
        });

        return convertView;
    }


    public ArrayList<Impayee> getSelectActorList(){
        ArrayList<Impayee> list = new ArrayList<>();
        for(int i=0;i<mydata.size();i++){
            if(mydata.get(i).isIsselected())
                list.add(mydata.get(i));
        }
        return list;
    }

}

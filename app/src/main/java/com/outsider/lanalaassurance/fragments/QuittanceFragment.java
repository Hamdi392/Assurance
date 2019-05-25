package com.outsider.lanalaassurance.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.outsider.lanalaassurance.R;


public class QuittanceFragment extends Fragment {

    Spinner spinner;
    Button btnquitance;
    TextView topnom, nom, etat, periode,num;
    public QuittanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_quittance, container, false);

        final String[] months = getResources().getStringArray(R.array.country_arrays);

        String numss = getArguments().getString("num");
        String noms = getArguments().getString("nom");
        String dates = getArguments().getString("date");
        String etatts = getArguments().getString("etat");
        String id = getArguments().getString("id");
        String codeclient = getArguments().getString("codeclient");

        spinner = convertView.findViewById(R.id.spinner1);
        btnquitance = convertView.findViewById(R.id.btncotisation);
        topnom = convertView.findViewById(R.id.policeNameTop);
        nom = convertView.findViewById(R.id.nomPolicecot);
        etat = convertView.findViewById(R.id.etatcot);
        periode = convertView.findViewById(R.id.periodecot);
        num = convertView.findViewById(R.id.numeroPolicecot);

        topnom.setText(noms);
        nom.setText(noms);
        num.setText(numss);
        etat.setText(etatts);
        periode.setText(dates);

        btnquitance.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                final LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                final TextView textView = new TextView(getContext());
                textView.setText("1000");
                textView.setTextSize(16);
                textView.setGravity(Gravity.CENTER);

                final SeekBar seekBar = new SeekBar(getContext());
                seekBar.setMax(99000);

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        int MIN = 1000;
                        textView.setText(String.valueOf(i+MIN));
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

                linearLayout.addView(textView);
                linearLayout.addView(seekBar);

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Cotisation Libre");
                    alertDialog.setMessage("Entrer le montant à payé");
                    alertDialog.setView(linearLayout);
                    alertDialog.setPositiveButton("Payer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                           // linearLayout.removeAllViews();
                        }
                    });
                    alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            //linearLayout.removeAllViews();
                        }
                    });
                    alertDialog.show();
            }
        });

        return convertView;
    }
}

package com.outsider.lanalaassurance;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsider.lanalaassurance.fragments.ContactFragment;
import com.outsider.lanalaassurance.fragments.DetailsFragment;
import com.outsider.lanalaassurance.fragments.HistoriqueFragment;
import com.outsider.lanalaassurance.fragments.ImpayeeFragment;
import com.outsider.lanalaassurance.fragments.QuittanceFragment;

public class ContractDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentTransaction fragmentTransaction;
    private TextView toolbarTV;
    private ImageView arrowback;
    String casviee= "", casdecess="", num="", nom="", datee="", id="", codeclient="", etatt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_details);

        arrowback = findViewById(R.id.arrowback);
        toolbarTV = findViewById(R.id.tvToolbarTitle);
        toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        casdecess = getIntent().getStringExtra("casdeces");
        casviee = getIntent().getStringExtra("casvie");
        num = getIntent().getStringExtra("num");
        nom = getIntent().getStringExtra("nom");
        datee = getIntent().getStringExtra("date");
        etatt = getIntent().getStringExtra("etat");

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        id = prefs.getString("id", null);
        codeclient = prefs.getString("codeclient", null);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("casdeces", casdecess);
        bundle.putString("casvie", casviee);
        bundle.putString("num", num);
        bundle.putString("nom", nom);
        bundle.putString("date", datee);
        bundle.putString("id", id);
        bundle.putString("codeclient", codeclient);
        bundle.putString("etat", etatt);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, detailsFragment);
        fragmentTransaction.commit();

        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.dtailseItem:

                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("casdeces", casdecess);
                    bundle.putString("casvie", casviee);
                    bundle.putString("num", num);
                    bundle.putString("nom", nom);
                    bundle.putString("date", datee);
                    bundle.putString("id", id);
                    bundle.putString("codeclient", codeclient);
                    bundle.putString("etat", etatt);
                    DetailsFragment detailsFragment = new DetailsFragment();
                    detailsFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.container, detailsFragment);
                    fragmentTransaction.commit();

                    changeTitle("Détaille");
                    return true;

                case R.id.histoItem:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("num", num);
                    bundle3.putString("id", id);
                    bundle3.putString("codeclient", codeclient);
                    HistoriqueFragment historiqueFragment = new HistoriqueFragment();
                    historiqueFragment.setArguments(bundle3);
                    fragmentTransaction.replace(R.id.container, historiqueFragment);
                    fragmentTransaction.commit();

                    changeTitle("Historique de versement");
                    return true;

                case R.id.recieptItem:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("num", num);
                    bundle2.putString("nom", nom);
                    bundle2.putString("date", datee);
                    bundle2.putString("id", id);
                    bundle2.putString("codeclient", codeclient);
                    bundle2.putString("etat", etatt);
                    QuittanceFragment quittanceFragment = new QuittanceFragment();
                    quittanceFragment.setArguments(bundle2);
                    fragmentTransaction.replace(R.id.container, quittanceFragment);
                    fragmentTransaction.commit();

                    changeTitle("Cotisation Libre");
                    return true;

                case R.id.contactItem:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, new ContactFragment());
                    fragmentTransaction.commit();

                    changeTitle("Contact Conseilé");
                    return true;

                case R.id.quittanceimpaye:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, new ImpayeeFragment());
                    fragmentTransaction.commit();

                    changeTitle("Quittance impayée");
                    return true;
            }
            return false;
        }
    };

    private void changeTitle(String title) {
        toolbarTV.setText(title);
    }

}

package com.outsider.lanalaassurance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.outsider.lanalaassurance.Adapter.ListContartAdapter;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView myList;
    private ProgressDialog progressDialog;
    TextView nomHeader, emailHeader;
    NavigationView navview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("S'il vous plaît, attendez..");
        progressDialog.setTitle("Chargement");
        progressDialog.setCancelable(false);
        progressDialog.show();

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        String id = prefs.getString("id", null);
        String codeclient = prefs.getString("codeclient", null);

        navview = findViewById(R.id.nav_view);
        nomHeader = navview.getHeaderView(0).findViewById(R.id.nomheader);
        emailHeader = navview.getHeaderView(0).findViewById(R.id.emailheader);
        nomHeader.setText(prefs.getString("nom", ""));
        emailHeader.setText(prefs.getString("email", ""));

        myList = findViewById(R.id.contratList);
        final ArrayList<Police> policeArrayList = new ArrayList<>();

        Log.d("iddddddd------ ", id);
        Log.d("Code Client ------ ", codeclient);
        if (id != null) {
            Ion.with(Main2Activity.this)
                    .load("https://edi.proassur.tn/api.proassur_vie/api/vie/GetListPoliceVie?UserId="+codeclient+"&codeSession="+id)
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
                                    Police[] polices = gson.fromJson(result.get("ListContratVie").toString(), Police[].class);
                                    for(Police police : polices){
                                        policeArrayList.add(police);
                                    }

                                    ListContartAdapter listContartAdapter = new ListContartAdapter(Main2Activity.this, policeArrayList);
                                    myList.setAdapter(listContartAdapter);

                                }else if(result.get("code").toString().equals("502")){
                                    Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    });
        }

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView casvie = view.findViewById(R.id.casvie);
                TextView casdeces = view.findViewById(R.id.casdeces);
                TextView num = view.findViewById(R.id.numPolice);
                TextView nom = view.findViewById(R.id.productId);
                TextView date = view.findViewById(R.id.duDatePolice);
                TextView etat = view.findViewById(R.id.etatIdPolice);

                Intent intent = new Intent(Main2Activity.this, ContractDetailsActivity.class);
                intent.putExtra("casvie", casvie.getText().toString());
                intent.putExtra("casdeces", casdeces.getText().toString());
                intent.putExtra("num", num.getText().toString());
                intent.putExtra("nom", nom.getText().toString());
                intent.putExtra("date", date.getText().toString());
                intent.putExtra("etat", etat.getText().toString());
                startActivity(intent);
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_account) {

            Intent intent =  new Intent(Main2Activity.this, ProfilActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_localisation) {

            Intent intent =  new Intent(Main2Activity.this, MapsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_info) {

            Intent intent =  new Intent(Main2Activity.this, InfoActivity.class);
            startActivity(intent);

        }  else if (id == R.id.nav_logout) {

            Intent intent =  new Intent(Main2Activity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.ptoastnote.toastnote;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private String mensaje;
    private int spPos;
    private int oport;

    private Spinner spTime;
    private EditText etMensaje;
    private TextView tvOport;

    private AdView adView;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        MobileAds.initialize(this, getString(R.string.id_app));
        mensaje = sp.getString(getString(R.string.pref_mensaje_key),"E=m*c^2");
        spPos = sp.getInt(getString(R.string.pref_time_key),0);
        oport = sp.getInt(getString(R.string.pref_oport_key),5);
        iniciarViews();
        adView = findViewById(R.id.adBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        iniciarToast();
    }
    private void iniciarToast(){
        if (sp.getBoolean(ServiceToast.TOAST_EXIST, false)) {
            stopService(new Intent(this, ServiceToast.class));
            startService(new Intent(this, ServiceToast.class));
        } else {
            startService(new Intent(this, ServiceToast.class));
        }
    }

    public void onClickAceptar(View view){
        mensaje = etMensaje.getText().toString();
        if (!mensaje.equals("")) {
            SharedPreferences.Editor e = sp.edit();
            e.putString(getString(R.string.pref_mensaje_key), mensaje);
            e.commit();
            iniciarToast();
            int c = sp.getInt(getString(R.string.pref_oport_key),5) - 1;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(getString(R.string.pref_oport_key),c);
            editor.commit();
            tvOport.setText(String.valueOf(c));
        }else{
            Toast.makeText(this, "Mensaje vacio :c", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,ServiceToast.class));
    }

    private void aumentarOP(){
        int c = sp.getInt(getString(R.string.pref_oport_key),5)+5;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(getString(R.string.pref_oport_key),c);
        editor.commit();
        tvOport.setText(String.valueOf(c));
    }
    public void onClickVerPub(View view){
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            if (interstitialAd.isLoading()) {
                Toast.makeText(this, R.string.sms_anuncio_cargando, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.sms_anuncio_no_disponible, Toast.LENGTH_SHORT).show();
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        }
    }
    private void iniciarViews(){
        spTime = findViewById(R.id.spTime);
        etMensaje= findViewById(R.id.etMensaje);
        tvOport = findViewById(R.id.tvOport);

        tvOport.setText(String.valueOf(oport));
        spTime.setSelection(spPos);

        interstitialAd = new InterstitialAd(this);

        interstitialAd.setAdUnitId(getString(R.string.id_intersticial));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                aumentarOP();
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(getString(R.string.pref_time_key),position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void onResume() {
        super.onResume();
        oport = sp.getInt(getString(R.string.pref_oport_key),5);
        tvOport.setText(String.valueOf(oport));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.contacto:{
                startActivity(new Intent(this, ContactoActivity.class));
                break;
            }
            case  R.id.about:{
                alertDialog().show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public AlertDialog alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ToastNote").
                setMessage("Desarrollador:\n\tAlexander RC \n Contacto: \n \t AlexRodriguez734m0@gmail.com").
                setPositiveButton("Listo", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

}

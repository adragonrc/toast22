package com.ptoastnote.toastnote;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactoActivity extends AppCompatActivity {
    private EditText etAsunto;
    private EditText etMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        etAsunto = findViewById(R.id.etAsunto);
        etMensaje = findViewById(R.id.etMensaje);
        (findViewById(R.id.btEnviarDatos)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarCorreo();
            }
        });
    }
    private void enviarCorreo(){
        String[] TO = {"alexrodriguez734m0@gmail.com"};
        String[] CC ={""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,TO);
        emailIntent.putExtra(Intent.EXTRA_CC,CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,etAsunto.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT,etMensaje.getText().toString());
        try{
            startActivity(Intent.createChooser(emailIntent,"GMAIL"));
            //finish();
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(this, "No hay clientes instalados :c", Toast.LENGTH_SHORT).show();
        }
    }
}

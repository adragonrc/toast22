package com.ptoastnote.toastnote;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class MyTask extends AsyncTask<String, String, String> {

    private String mensaje;
    private long time;
    private Context context;

    public MyTask(String mensaje, long time, Context context){
        this.mensaje = mensaje;
        this.time = time;
        this.context = context;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        while (!isCancelled()){
            try {
                publishProgress(mensaje);
                // Stop 5s
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //          Toast.makeText(ToastService.this, "ask finalizado", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}

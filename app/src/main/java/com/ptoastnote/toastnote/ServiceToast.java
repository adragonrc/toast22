package com.ptoastnote.toastnote;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class ServiceToast extends Service {
    private MyTask myTask;
    public static final String TOAST_EXIST = "toast_service_exist";
    SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String mensaje = sp.getString(getString(R.string.pref_mensaje_key),"Primary default");
        int changeType = sp.getInt(getString(R.string.pref_time_key),2);
        long time ;
        switch (changeType){
            case 0:{
                time = 15000; break;
            }
            case 1:{
                time = 30000; break;
            }
            case 2:{
                time = 60000; break;
            }
            case 3:{
                time = 120000; break;
            }
            case 4:{
                time = 300000; break;
            }
            default: {
                time = 30000; break;
            }
        }
        myTask = new MyTask(mensaje,time,this);
        myTask.execute();
        SharedPreferences.Editor e =  sp.edit();
        e.putBoolean(TOAST_EXIST,true);
        e.commit();
     //   Toast.makeText(this, "Servicio iniciado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        myTask.cancel(true);
        SharedPreferences.Editor e =  sp.edit();
        e.putBoolean(TOAST_EXIST,false);
        e.commit();
        // Anular el registro de screenOnOffReceiver cuando se destruye.
      //  Toast.makeText(this, "Servicio Destruido", Toast.LENGTH_SHORT).show();
    }
    public MyTask getMyTask(){
        return myTask;
    }


}


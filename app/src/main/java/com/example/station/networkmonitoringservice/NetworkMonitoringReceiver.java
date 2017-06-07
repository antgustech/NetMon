package com.example.station.networkmonitoringservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Receiver that messages the user that the connection has changed.
 */

public class NetworkMonitoringReceiver extends BroadcastReceiver {

    private boolean registered;

    /**
     * When this broadcast receiver receives something, message the user about it.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
      try{
          Thread.sleep(8000);
      }catch(Exception e){
      }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null){
            if( netInfo.getType()  ==  ConnectivityManager.TYPE_MOBILE){
                AudioManager manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                if(manager.isMusicActive())
                {
                    Toast.makeText(context, context.getString(R.string.networkChange), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * Unregisters this broadcast from the manifest.
     *
     * @param context - context
     * @return true if was registered else false
     */
    public void unregister(Context context) {

        if(registered){
            context.unregisterReceiver(this);
            registered = false;
        }
    }

    /**
     * Registers this broadcast to the manifest.
     * @param context
     * @param filter
     */
    public void registerReceiver(Context context, IntentFilter filter) {
        if (!registered) {
            context.registerReceiver(this, filter);
            }
        registered = true;
    }
}


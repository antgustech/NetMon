package com.example.station.networkmonitoringservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
        Toast.makeText(context, context.getString(R.string.networkChange), Toast.LENGTH_LONG).show();
    }

    /**
     * Unregisters this broadcast from the manifest.
     *
     * @param context - context
     * @return true if was registered else false
     */
    public void unregister(Context context) {
        context.unregisterReceiver(this);
        registered = false;
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


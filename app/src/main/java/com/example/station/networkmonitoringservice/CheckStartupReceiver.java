package com.example.station.networkmonitoringservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

/**
 * Receiver that is called upon boot. Will register the NetworkMonitoringReceiver automaticly.
 */

public class CheckStartupReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences settings = context.getSharedPreferences(Utility.PREFS_NAME, 0);
        final boolean Registered = settings.getBoolean(Utility.PREF_VALUE, false);
        if (Registered) {
            NetworkMonitoringReceiver receiver = new NetworkMonitoringReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Utility.BR_WIFI);
            receiver.registerReceiver(context.getApplicationContext(), filter);
        }
    }
}

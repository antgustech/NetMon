package com.example.station.networkmonitoringservice;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * MainActivity that handles the UI and main application.
 */
public class MainActivity extends AppCompatActivity {

    private ToggleButton btnStartStop;
    private ImageView imgStatus;
    private TextView txtStatus;
    private Context ctx;


    /**
     * Setup the app.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setListeners();
    }

    /**
     * Initializes buttons and text elements by getting the old saved state.
     */
    private void initialize(){

        btnStartStop = (ToggleButton) findViewById(R.id.btnStartStop);
        imgStatus = (ImageView) findViewById(R.id.imgStatus);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        ctx = getApplicationContext();
        boolean registered = getState();

        if (registered) {
            txtStatus.setText(getString(R.string.running));
            imgStatus.setImageResource(R.drawable.ic_done_white_24dp);
            imgStatus.setColorFilter(ContextCompat.getColor(ctx,R.color.green));
            btnStartStop.setChecked(false);
        } else {
            txtStatus.setText(getString(R.string.notRunning));
            imgStatus.setImageResource(R.drawable.ic_error_outline_white_24dp);
            imgStatus.setColorFilter(ContextCompat.getColor(ctx, R.color.red));
            btnStartStop.setChecked(true);
        }
    }

    /**
     * Sets the listener for the button.
     */
    private void setListeners(){
        final NetworkMonitoringReceiver myReceiver = new NetworkMonitoringReceiver();
        btnStartStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean registered = getState();

                if (registered) {
                    txtStatus.setText(getString(R.string.notRunning));
                    imgStatus.setImageResource(R.drawable.ic_error_outline_white_24dp);
                    imgStatus.setColorFilter(ContextCompat.getColor(ctx, R.color.red));
                    myReceiver.unregister(ctx);
                    changeState(false);
                } else {
                    txtStatus.setText(getString(R.string.running));
                    imgStatus.setImageResource(R.drawable.ic_done_white_24dp);
                    imgStatus.setColorFilter(ContextCompat.getColor(ctx,R.color.green));
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(Utility.BR_WIFI);
                    myReceiver.registerReceiver(ctx, filter);
                    changeState(true);
                }
            }
        });
    }

    /**
     * Saves a value to shared preferences so we can later check if we
     * should enable or disable the broadcast receiver.
     * @param state - a boolean that determines to save a true or false value.
     */
    private void changeState(boolean state) {
        SharedPreferences settings = getSharedPreferences(Utility.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Utility.PREF_VALUE, state);
        editor.apply();
    }

    /**
     *Returns the current state to determine if we should enable or disable the broadcast receiver.
     * @return - the current state as a boolean.
     */
    private boolean getState() {
        SharedPreferences settings = getSharedPreferences(Utility.PREFS_NAME, 0);
        return settings.getBoolean(Utility.PREF_VALUE, false);
    }
}
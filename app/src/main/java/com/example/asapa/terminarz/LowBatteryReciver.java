package com.example.asapa.terminarz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.ListView;
import android.widget.Toast;

public class LowBatteryReciver extends BroadcastReceiver {
    public LowBatteryReciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isLowBattery = status == BatteryManager.BATTERY_STATUS_DISCHARGING;
       if(isLowBattery)
           Toast.makeText(context, context.getResources().getString(R.string.low_battery), Toast.LENGTH_SHORT).show();
    }
}

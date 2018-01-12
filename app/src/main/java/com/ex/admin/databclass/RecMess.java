package com.ex.admin.databclass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RecMess extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, ServiceMess.class));
    }
}

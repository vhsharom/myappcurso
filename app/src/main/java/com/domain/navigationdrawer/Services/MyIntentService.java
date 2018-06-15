package com.domain.navigationdrawer.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int value = intent.getIntExtra("value", 0);
        for (int i = 0; i < value; i++){
            //Log.d("App", "i = " + i);
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("App", "Service destroy");
    }
}

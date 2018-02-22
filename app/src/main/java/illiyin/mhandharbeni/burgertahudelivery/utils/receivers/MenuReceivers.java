package illiyin.mhandharbeni.burgertahudelivery.utils.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 12/21/17.
 */

public class MenuReceivers extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String mode = bundle.getString("MODE");
        Log.d(TAG, "onReceive: MenuReceivers "+mode);
        switch (mode){
            case "UPDATE MENU":
                break;
            case "UPDATE LIST":
                break;
            default:
                break;
        }


    }
};
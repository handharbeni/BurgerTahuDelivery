package illiyin.mhandharbeni.servicemodule.service.intentservice;

import android.app.IntentService;
import android.content.Intent;

import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.ModelOrder;

/**
 * Created by root on 06/08/17.
 */

public class TrackingService extends IntentService {
    AdapterModel adapterModel;
    public TrackingService() {
        super("Outlet Service");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        adapterModel = new AdapterModel(getBaseContext());
        sendBroadCast();
        return super.onStartCommand(intent, flags, startId);
    }
    public void sendBroadCast(){
        this.sendBroadcast(new Intent().setAction("MAPS SERVICE").putExtra("MODE", "UPDATE LOC"));
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        adapterModel.trackKurir();
    }
}
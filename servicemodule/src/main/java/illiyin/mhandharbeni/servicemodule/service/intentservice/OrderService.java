package illiyin.mhandharbeni.servicemodule.service.intentservice;

import android.app.IntentService;
import android.content.Intent;

import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.ModelOrder;
import illiyin.mhandharbeni.databasemodule.ModelOutlet;

/**
 * Created by root on 22/07/17.
 */

public class OrderService extends IntentService {
    ModelOrder modelOrder;
    AdapterModel adapterModel;
    public OrderService() {
        super("Outlet Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        modelOrder = new ModelOrder();
        adapterModel = new AdapterModel(getBaseContext());
        sendBroadCast();
        return super.onStartCommand(intent, flags, startId);
    }
    public void sendBroadCast(){
        this.sendBroadcast(new Intent().setAction("SERVICE ORDER").putExtra("MODE", "UPDATE ORDER"));
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        adapterModel.syncOrder(modelOrder);
    }
}
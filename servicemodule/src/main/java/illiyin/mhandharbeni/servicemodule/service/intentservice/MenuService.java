package illiyin.mhandharbeni.servicemodule.service.intentservice;

import android.app.IntentService;
import android.content.Intent;

import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.ModelMenu;
import illiyin.mhandharbeni.servicemodule.service.MainService;

/**
 * Created by root on 17/07/17.
 */

public class MenuService extends IntentService {
    public static final String
            ACTION_LOCATION_BROADCAST = MainService.class.getName();
    ModelMenu modelMenu;
    AdapterModel adapterModel;
    public MenuService() {
        super("Menu Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        modelMenu = new ModelMenu();
        adapterModel = new AdapterModel(getBaseContext());
        sendBroadCast();
        return super.onStartCommand(intent, flags, startId);
    }
    public void sendBroadCast(){
        this.sendBroadcast(new Intent().setAction("SERVICE MENU").putExtra("MODE", "UPDATE MENU"));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        adapterModel.syncDataMenu(modelMenu);
    }
}

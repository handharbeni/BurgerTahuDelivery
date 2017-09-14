package illiyin.mhandharbeni.burgertahudelivery.fragment.sub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.burgertahudelivery.adapter.OrderAdapter;
import illiyin.mhandharbeni.burgertahudelivery.adapter.utils.CartUtil;
import illiyin.mhandharbeni.databasemodule.ModelOrder;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.servicemodule.service.intentservice.MenuService;
import io.realm.RealmResults;

/**
 * Created by root on 9/3/17.
 */

public class OrderHistory extends AppCompatActivity implements CartUtil {
    ModelOrder modelOrder;
    Crud crud;

    List<ModelOrder> lstOrder;
    OrderAdapter orderAdapter;

    ToggleSwitch kindOrder;

    RecyclerView listOrder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelOrder = new ModelOrder();
        crud = new Crud(getApplicationContext(), modelOrder);
        registerReceiver(this.receiver, new IntentFilter("SERVICE ORDER"));
        setContentView(R.layout._layout_orderhistory);
        kindOrder = (ToggleSwitch) findViewById(R.id.kindOrder);
        listOrder = (RecyclerView) findViewById(R.id.listOrder);

        kindOrder.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                initDataOrder(position);
            }
        });
    }



    private void initDataOrder(int position){
        lstOrder = new ArrayList<>();
        String[] statusOrder = null;
        if (position == 0){
            statusOrder = new String[]{"1", "2", "3", "4"};
        }else{
            statusOrder = new String[]{"5", "6"};
        }
        RealmResults result = crud.read("status", statusOrder);
        if (result.size() > 0){
            for (int i=0;i<result.size();i++){
                modelOrder = (ModelOrder) result.get(i);
                lstOrder.add(modelOrder);
            }
        }
        listData();
        initLayoutManager();
    }
    public void listData(){
        orderAdapter = new OrderAdapter(getApplicationContext(), lstOrder, this);
        listOrder.setAdapter(orderAdapter);
    }
    public void initLayoutManager(){
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listOrder.setLayoutManager(llm);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceiver(this.receiver, new IntentFilter("SERVICE ORDER"));
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(MenuService.ACTION_LOCATION_BROADCAST)
        );
    }

    @Override
    public void onPause() {
        crud.closeRealm();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void onStop() {
        crud.closeRealm();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        crud.closeRealm();
        unregisterReceiver(this.receiver);
        super.onDestroy();
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String mode = bundle.getString("MODE");
            switch (mode){
                case "UPDATE ORDER":
                    initDataOrder(kindOrder.getCheckedTogglePosition());
                    break;
                default:
                    break;
            }


        }
    };

    @Override
    public void updateInfo() {

    }

}


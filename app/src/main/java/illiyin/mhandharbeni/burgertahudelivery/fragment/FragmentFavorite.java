package illiyin.mhandharbeni.burgertahudelivery.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.burgertahudelivery.adapter.MenuItemAdapter;
import illiyin.mhandharbeni.burgertahudelivery.adapter.decoration.DividerItemDecoration;
import illiyin.mhandharbeni.databasemodule.ModelMenu;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.servicemodule.service.intentservice.MenuService;
import io.realm.RealmResults;

/**
 * Created by root on 8/28/17.
 */

public class FragmentFavorite  extends Fragment {
    View v;

    ModelMenu modelMenu;
    Crud crud;

    List<ModelMenu> listMenu;
    MenuItemAdapter menuItemAdapter;
    RecyclerView rvMenu;
    View destination;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().registerReceiver(this.receiver, new IntentFilter("SERVICE MENU"));
        enableReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout._layoutmenu, container, false);

        destination = (View) v.findViewById(R.id.destination);
        destination.setVisibility(View.GONE);

        modelMenu = new ModelMenu();
        crud = new Crud(getActivity().getApplicationContext(), modelMenu);

        initData();
        listData();
        initLayoutManager();

        return v;
    }

    public void initData(){
        listMenu = new ArrayList<>();
        RealmResults resultMenu = crud.read();
        if (resultMenu.size() > 0){
            for (int i=0;i<resultMenu.size();i++){
                modelMenu = (ModelMenu) resultMenu.get(i);
                listMenu.add(new ModelMenu(modelMenu.getId(),
                        modelMenu.getNama(),
                        modelMenu.getGambar(),
                        modelMenu.getHarga(),
                        modelMenu.getKategori(),
                        modelMenu.getSha()
                ));
            }
        }
    }

    public void initDataAdapter(){
        listMenu = new ArrayList<>();
        RealmResults resultMenu = crud.read();
        if (resultMenu.size() > 0){
            for (int i=0;i<resultMenu.size();i++){
                modelMenu = (ModelMenu) resultMenu.get(i);
                listMenu.add(new ModelMenu(modelMenu.getId(),
                        modelMenu.getNama(),
                        modelMenu.getGambar(),
                        modelMenu.getHarga(),
                        modelMenu.getKategori(),
                        modelMenu.getSha()
                ));
            }
        }
        menuItemAdapter.swap(listMenu);
//        menuItemAdapter.notifyDataSetChanged();
    }
    public void listData(){
        rvMenu = (RecyclerView) v.findViewById(R.id.listMenu);
        menuItemAdapter = new MenuItemAdapter(getActivity().getApplicationContext(), listMenu);
        rvMenu.setAdapter(menuItemAdapter);
    }
    public void initLayoutManager(){
        int decorPriorityIndex = 0;
        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.divider_menu);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenu.addItemDecoration(dividerItemDecoration, decorPriorityIndex);
        rvMenu.setLayoutManager(llm);
    }

    @Override
    public void onStart() {
        super.onStart();
        enableReceiver();
//        getActivity().registerReceiver(this.receiver, new IntentFilter("SERVICE MENU"));
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((receiver),
//                new IntentFilter(MenuService.ACTION_LOCATION_BROADCAST)
//        );
    }

    @Override
    public void onPause() {
//        getActivity().unregisterReceiver(this.receiver);
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        disableReceiver();
        crud.closeRealm();
        super.onPause();
    }

    @Override
    public void onStop() {
//        getActivity().unregisterReceiver(this.receiver);
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        disableReceiver();
        crud.closeRealm();
        super.onStop();
    }

    @Override
    public void onDestroy() {
//        getActivity().unregisterReceiver(this.receiver);
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        disableReceiver();
        crud.closeRealm();
        super.onDestroy();
    }
    public static class Receivers extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String mode = bundle.getString("MODE");
            switch (mode){
                case "UPDATE MENU":
                    FragmentFavorite fragmentFavorite = new FragmentFavorite();
                    fragmentFavorite.initDataAdapter();
                    break;
                case "UPDATE LIST":

                    break;
                default:

                    break;
            }


        }
    };
    private void enableReceiver(){
        ComponentName component = new ComponentName(this.getActivity().getApplicationContext(), Receivers.class);
        this.getActivity().getPackageManager().setComponentEnabledSetting(component, PackageManager. COMPONENT_ENABLED_STATE_ENABLED , PackageManager.DONT_KILL_APP);
    }
    private void disableReceiver(){
        ComponentName component = new ComponentName(this.getActivity().getApplicationContext(), Receivers.class);
        this.getActivity().getPackageManager().setComponentEnabledSetting(component, PackageManager. COMPONENT_ENABLED_STATE_DISABLED , PackageManager.DONT_KILL_APP);
    }

}
package illiyin.mhandharbeni.burgertahudelivery.fragment.sub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.burgertahudelivery.adapter.CartItemAdapter;
import illiyin.mhandharbeni.burgertahudelivery.adapter.decoration.DividerItemDecoration;
import illiyin.mhandharbeni.burgertahudelivery.adapter.utils.CartUtil;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.model.ModelAddress;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.ModelCart;
import illiyin.mhandharbeni.databasemodule.ModelOutlet;
import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import io.realm.RealmResults;
import okhttp3.*;


/**
 * Created by root on 25/07/17.
 */

public class Cart extends AppCompatActivity implements CartUtil, SessionListener {
    int PLACE_PICKER_REQUEST = 1;
    private Integer RESULT_OK = -1, RESULT_CANCEL=0;
    private static String LATDESTI = "LATDESTI";
    private static String LONGDESTI = "LONGDESTI";
    private static String ADDRESSDESTI = "ADDRESDESTI";
    private static String ZIPCODEDESTI = "ZIPCODEDESTI";
    private static String DISTANCE = "DISTANCE", DELIVERYFEE = "DELIVERYFEE", FEEDELIV = "FEEDELIV";
    private String ADDRESS = "ADDRESS";

    private static final String TAG = "CartActivity";
    private ImageView imgBack;
    private RelativeLayout addMores;
    private Button btnOrder;

    private Crud crudCart;
    private ModelCart modelCart;

    private Crud crudOutlet;
    private ModelOutlet modelOutlet;

    private List<ModelCart> modelCartList;

    private RecyclerView listCart;
    private TextView changeDestination;

    private Session session;

    private List<ModelAddress> outlet;
    private List<ModelAddress> sortOutlet;

    private String endpointorder;
    private ProgressDialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        endpointorder = getString(R.string.module_server)+"users/order";

        modelCart = new ModelCart();
        dialog = new ProgressDialog(this);
        crudCart = new Crud(this, modelCart);
        modelOutlet = new ModelOutlet();
        crudOutlet = new Crud(this, modelOutlet);
        session = new Session(getApplicationContext(), this);
        setContentView(R.layout._layoutcart);

        listCart = (RecyclerView) findViewById(R.id.listCart) ;
        addMores = (RelativeLayout) findViewById(R.id.addMores);
        addMores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppCompatActivity act = (AppCompatActivity) getParent();
//                Fragment fragment = new FragmentMenu();
//                int position = 0;
//                ((MainActivity) act).changeFragmentAndBottomBar(fragment, 0);
//                sessionListener.sessionChange();
                finish();
            }
        });

        changeDestination = (TextView) findViewById(R.id.changeDestination);
        changeDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placePicker();
            }
        });

        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new doOrderTask().execute();
//                doOrder();
            }
        });

        AdapterModel adapterModel = new AdapterModel(this);
        adapterModel.getFeeDeliv();

        initOutlet();
        initData();
        initAdapter();
        initLayoutManager();
        setInfoOutlet();
        updateInfo();
    }

    @Override
    protected void onDestroy() {
//        dismissDialog();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        dismissDialog();
        super.onPause();
    }

    @Override
    protected void onStop() {
        dismissDialog();
        super.onStop();
    }

    public void initOutlet(){
        outlet = new ArrayList<>();
        RealmResults results = crudOutlet.read();

        if (results.size()>0){
            for (int i=0;i<results.size();i++){
                ModelOutlet mo = (ModelOutlet) results.get(i);
                Integer random = new Random().nextInt(10) + 1;
                String alamat = mo.getAlamat();
                String id_oulet = String.valueOf(mo.getId());
                Double latitude = Double.valueOf(mo.getLatitude());
                Double longitude = Double.valueOf(mo.getLongitude());
                outlet.add(new ModelAddress(random, alamat, id_oulet, latitude, longitude));
            }
        }
    }

    public void initData(){
        modelCartList = new ArrayList<>();
        RealmResults result = crudCart.read();
        if (result.size() > 0){
            for (int i=0;i<result.size();i++){
                ModelCart model = (ModelCart) result.get(i);
                modelCartList.add(model);
            }
        }
    }

    public void initAdapter(){
        listCart = (RecyclerView) findViewById(R.id.listCart);
        CartItemAdapter ma = new CartItemAdapter(getApplicationContext(), modelCartList, this);
        listCart.setAdapter(ma);
        listCart.setNestedScrollingEnabled(false);
    }
    public void initLayoutManager() {
        int decorPriorityIndex = 0;
        Drawable dividerDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider_menu);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listCart.addItemDecoration(dividerItemDecoration, decorPriorityIndex);
        listCart.setLayoutManager(llm);
    }

    @Override
    public void updateInfo() {
        RealmResults results = crudCart.read();
        int total_harga_item = 0;
        int biaya_kirim = (session.getCustomParams(DISTANCE, 0)/1000) * session.getCustomParams(FEEDELIV, 0);
        session.setCustomParams(DELIVERYFEE, biaya_kirim);
        int total_harga = 0;
        if (results.size() > 0){
            for (int i=0;i<results.size();i++){
                ModelCart modelCart = (ModelCart) results.get(i);
                int total = 0;
                total = modelCart.getJumlah() * Integer.valueOf(modelCart.getHarga());
                total_harga_item += total;
            }
        }

        total_harga = total_harga_item + biaya_kirim;

        TextView txtTotalHargaItem = (TextView) findViewById(R.id.totalhargaitem);
        TextView txtDeliveryFee = (TextView) findViewById(R.id.deliveryfee);
        TextView txtTotalHarga = (TextView) findViewById(R.id.totalharga);

        txtTotalHargaItem.setText(illiyin.mhandharbeni.utilslibrary.NumberFormat.format(Double.valueOf(String.valueOf(total_harga_item))));
        txtDeliveryFee.setText(illiyin.mhandharbeni.utilslibrary.NumberFormat.format(Double.valueOf(String.valueOf(biaya_kirim))));
        txtTotalHarga.setText(illiyin.mhandharbeni.utilslibrary.NumberFormat.format(Double.valueOf(String.valueOf(total_harga))));

        TextView totalSemua = (TextView) findViewById(R.id.totalSemua);
        totalSemua.setText(illiyin.mhandharbeni.utilslibrary.NumberFormat.format(Double.valueOf(String.valueOf(total_harga))));
    }


    @Override
    public void sessionChange() {
        updateInfo();
    }

    public void calculateDistance(){
        sortOutlet = new ArrayList<>();
        illiyin.mhandharbeni.utilslibrary.Address address = new illiyin.mhandharbeni.utilslibrary.Address(this);
        for (int i=0;i<outlet.size();i++){
            Double latOutlet = outlet.get(i).getLatitude();
            Double longOutlet = outlet.get(i).getLongitude();
            String addres1 = outlet.get(i).getAlamat();

            Double latdesti = Double.valueOf(session.getCustomParams(LATDESTI, "-7.9826195"));
            Double longdesti = Double.valueOf(session.getCustomParams(LONGDESTI, "112.6287"));
            String addres2 = session.getCustomParams(ADDRESSDESTI, "");

            String id_outlet = outlet.get(i).getId_outlet();

            String addresoutlet = address.getCurrentAddress(latOutlet, longOutlet);
            String addrescustomer = address.getCurrentAddress(latdesti, longdesti);
            Integer distance = address.getDistance(addres1, addres2, getString(R.string.keyDistance));

            sortOutlet.add(new ModelAddress(distance, outlet.get(i).getAlamat(), id_outlet, latOutlet, longOutlet));
        }
        sortOutlets();
        setInfoOutlet();
        updateInfo();
    }
    public void sortOutlets(){
        Collections.sort(sortOutlet, new Comparator<ModelAddress>() {
            @Override
            public int compare(ModelAddress o1, ModelAddress o2) {
                return o1.getDistance().compareTo(o2.getDistance());
            }
        });
//        Log.d(TAG, "sortOutlets: "+session.getCustomParams(ADDRESSDESTI, ""));
//        Log.d(TAG, "sortOutlets: "+session.getCustomParams(LATDESTI, ""));
//        Log.d(TAG, "sortOutlets: "+session.getCustomParams(LONGDESTI, ""));
        for (int i =0;i<sortOutlet.size();i++){
            Log.d(TAG, "sortOutlets: "+sortOutlet.get(i).getDistance()+"/"+sortOutlet.get(i).getAlamat());
        }
        session.setCustomParams(DISTANCE, sortOutlet.get(0).getDistance());
        session.setCustomParams(ADDRESS, sortOutlet.get(0).getAlamat());
    }
    public void setInfoOutlet(){
        TextView detailOutlet, address, distance;
        detailOutlet = (TextView) findViewById(R.id.detailOutlet);
        address = (TextView) findViewById(R.id.address);
        distance = (TextView) findViewById(R.id.distance);

        detailOutlet.setText(session.getCustomParams(ADDRESS, "Burger Tahu Malang Suhat 2"));
        address.setText(session.getCustomParams(ADDRESSDESTI, "Malang"));
        distance.setText(String.valueOf(session.getCustomParams(DISTANCE, 0)/1000)+" Km");
    }
    public void testsortoutlet(){
        Collections.sort(sortOutlet, new Comparator<ModelAddress>() {
            @Override
            public int compare(ModelAddress o1, ModelAddress o2) {
                return o1.getDistance().compareTo(o2.getDistance());
            }
        });
        for (int i=0;i<sortOutlet.size();i++){
            Log.d(TAG, "testStringOutlet: "+sortOutlet.get(i).getDistance()+"/"+sortOutlet.get(i).getAlamat()+"/"+sortOutlet.get(i).getId_outlet());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                session.setCustomParams(LATDESTI, String.valueOf(place.getLatLng().latitude));
                session.setCustomParams(LONGDESTI, String.valueOf(place.getLatLng().longitude));
                session.setCustomParams(ADDRESSDESTI, String.valueOf(place.getAddress()));
                session.setCustomParams(ZIPCODEDESTI, String.valueOf(place.getLocale()));
                calculateDistance();
            }
        }
    }
    private void doOrder(){
        calculateDistance();
        EditText notesaddres = (EditText) findViewById(R.id.notesaddres);

        String token = session.getToken();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("method", "new_order")
                .add("id_outlet", sortOutlet.get(0).getId_outlet())
                .add("alamat", session.getCustomParams(ADDRESSDESTI, "MALANG"))
                .add("latitude", session.getCustomParams(LATDESTI, "-7,96662"))
                .add("longitude", session.getCustomParams(LONGDESTI, "112,633"))
                .add("delivery_fee", String.valueOf(session.getCustomParams(DELIVERYFEE, 0)))
                .add("keterangan", notesaddres.getText().toString())
                .build();
        CallHttp callHttp = new CallHttp(getApplicationContext());
        String response = callHttp.post(endpointorder, formBody);
        try {
            JSONObject jsonObject = new JSONObject(response);
            Boolean returns = jsonObject.getBoolean("return");
            if (returns){
                Integer id_order = jsonObject.getInt("data");
//                postItemOrder(id_order);
            }
        } catch (NullPointerException | JSONException e) {
            e.printStackTrace();
        }

    }
    private void deleteAllRealm(){
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
                crudCart.deleteAll(ModelCart.class);
//            }
//        });
    }
    private void postItemOrder(Integer id) throws JSONException {
        AdapterModel adapterModel = new AdapterModel(this);
        adapterModel.getFeeDeliv();

        RealmResults results = crudCart.read();
        if (results.size()>0){
            for (int i=0; i<results.size();i++){
                ModelCart mc = (ModelCart) results.get(i);
                String token = session.getToken();
                String method = "add_item";
                String id_order = String.valueOf(id);
                String id_menu = String.valueOf(mc.getId());
                String jumlah = String.valueOf(mc.getJumlah());

                RequestBody requestBody = new FormBody.Builder()
                        .add("token", token)
                        .add("method", method)
                        .add("id_order", id_order)
                        .add("id_menu", id_menu)
                        .add("jumlah", jumlah)
                        .build();
                CallHttp callHttp = new CallHttp(getApplicationContext());
                String response = callHttp.post(endpointorder, requestBody);
                JSONObject jsonObject = new JSONObject(response);
                boolean returns = jsonObject.getBoolean("return");
                if (returns){
                    Toast.makeText(this, "Pesanan Anda Sedang Diproses, Terima Kasih", Toast.LENGTH_SHORT).show();
                    /*add item succesfully*/
                    /*delete from local*/
//                    crudCart.delete("id", mc.getId());
                }
            }
        }
    }

    private void placePicker(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void showDialog(){
        dialog.setMessage("ORDERING, PLEASE WAIT");
        dialog.setCancelable(false);
        dialog.show();
    }

    public Boolean statusDialog(){
        return dialog.isShowing();
    }

    public void dismissDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Fragment fragment = new FragmentMenu();
//        Integer position = 1;
//        ((MainActivity)getParent()).changeFragmentAndBottomBar(fragment, position);
//        MainActivity.changeFragmentAndBottomBar(fragment, position);
//        finish();
    }
    class doOrderTask extends AsyncTask<String, Void, Boolean> {
        doOrderTask(){
            dialog = new ProgressDialog(Cart.this);
        }

        protected void onPreExecute() {
            showDialog();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
            if (statusDialog()){
                deleteAllRealm();
                dismissDialog();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            doOrder();
            return true;
        }
    }
}

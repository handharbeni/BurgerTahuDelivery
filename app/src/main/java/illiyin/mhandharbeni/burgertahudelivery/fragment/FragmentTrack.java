package illiyin.mhandharbeni.burgertahudelivery.fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.burgertahudelivery.adapter.OrderAdapter;
import illiyin.mhandharbeni.databasemodule.ModelOrder;
import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;

/**
 * Created by root on 8/26/17.
 */

public class FragmentTrack extends Fragment {
    View v;
    private static final String TAG = "SearchOrder";
    private String endpointsearch = "";
    private EditText txtSearch;
    private RecyclerView listOrder;
    private Session session;
    private CallHttp call;
    List<ModelOrder> lstOrder;
    OrderAdapter orderAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        v = inflater.inflate(R.layout._layouttrackorder, container, false);
        session = new Session(getActivity().getApplicationContext(), new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });
        call = new CallHttp(getActivity().getApplicationContext());
        endpointsearch += getString(R.string.server);
        endpointsearch += "/users/search";

        listOrder = (RecyclerView) v.findViewById(R.id.listOrder);
        txtSearch = (EditText) v.findViewById(R.id.search);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                doSearch();
            }
        });
        return v;
    }
    private void doSearch(){
        lstOrder = new ArrayList<>();
        if (!txtSearch.getText().toString().isEmpty()){
            String token  = session.getToken();
            String id_order = txtSearch.getText().toString();
            endpointsearch += "?id_order="+id_order+"&token="+token;

            String response = call.get(endpointsearch);
            try {
                JSONObject jsonObject = new JSONObject(response);
                Boolean returns = jsonObject.getBoolean("return");
                if (returns){
                    JSONArray arrayData = jsonObject.getJSONArray("data");
                    if (arrayData.length()>0){
                        for (int i=0;i<arrayData.length();i++){
//                            Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                            JSONObject dataOrder = arrayData.getJSONObject(i);
                            ModelOrder modelOrder = new ModelOrder();
                            modelOrder.setId(Integer.valueOf(dataOrder.getString("id_order")));
                            modelOrder.setId_user(Integer.valueOf(dataOrder.getString("id_user")));
                            if (!dataOrder.getString("kurir").equalsIgnoreCase("nothing")){
                                JSONArray arrayKurir = dataOrder.getJSONArray("kurir");
                                if (arrayKurir.length() > 0){
                                    for (int j=0;j<arrayKurir.length();j++){
                                        JSONObject objectKurir = arrayKurir.getJSONObject(j);
                                        modelOrder.setId_kurir(Integer.valueOf(objectKurir.getString("id")));
                                    }
                                }else{
                                    modelOrder.setId_kurir(0);
                                }
                            }else{
                                modelOrder.setId_kurir(0);
                            }

                            JSONArray arrayOutlet = dataOrder.getJSONArray("outlet");
                            if (arrayOutlet.length()>0){
                                for (int k=0;k<arrayOutlet.length();k++){
                                    JSONObject objecOutlet = arrayOutlet.getJSONObject(k);
                                    modelOrder.setAlamat(objecOutlet.getString("outlet"));
                                    modelOrder.setLatitude(objecOutlet.getString("latitude"));
                                    modelOrder.setLongitude(objecOutlet.getString("longitude"));
                                }
                            }else{
                                modelOrder.setAlamat("");
                                modelOrder.setLatitude("");
                                modelOrder.setLongitude("");
                            }

                            modelOrder.setNama_customer(dataOrder.getString("nama_user"));
                            modelOrder.setLatitude_customer(dataOrder.getString("lat_order"));
                            modelOrder.setLongitude_customer(dataOrder.getString("long_order"));

                            modelOrder.setAlamat_customer(dataOrder.getString("alamat_order"));
                            modelOrder.setDelivery_fee(dataOrder.getString("delivery_fee"));

                            modelOrder.setEmail_customer(dataOrder.getString("email"));
                            modelOrder.setTotal_belanja(dataOrder.getString("total_belanja"));
                            modelOrder.setTanggal(dataOrder.getString("tanggal"));
                            modelOrder.setJam(dataOrder.getString("jam"));
                            modelOrder.setStatus(dataOrder.getJSONObject("status").getString("key"));
                            modelOrder.setSha(dataOrder.getString("sha"));
                            lstOrder.add(modelOrder);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listData();
            initLayoutManager();
        }
    }

    public void listData(){
        orderAdapter = new OrderAdapter(getActivity().getApplicationContext(), lstOrder);
        listOrder.setAdapter(orderAdapter);
        orderAdapter.clear();
    }
    public void initLayoutManager(){
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listOrder.setLayoutManager(llm);
    }

}
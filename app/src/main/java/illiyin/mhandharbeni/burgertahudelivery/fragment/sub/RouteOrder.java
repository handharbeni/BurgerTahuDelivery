package illiyin.mhandharbeni.burgertahudelivery.fragment.sub;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.drawroutemap.Navigator;

import static android.provider.Settings.Global.AIRPLANE_MODE_ON;

/**
 * Created by root on 11/08/17.
 */

public class RouteOrder extends AppCompatActivity implements OnMapReadyCallback {


    protected GoogleMap mMap;
    protected LatLng start;
    protected LatLng end;
    private static final String LOG_TAG = "MyActivity";

    private String nama, nama_outlet, latitude, latitude_outlet, longitude, longitude_outlet;

    private TextView txtOutlet, txtCustomer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        initParam();
        setContentView(R.layout._layout_routeorder);

        txtOutlet = (TextView) findViewById(R.id.txtOutlet);
        txtCustomer = (TextView) findViewById(R.id.txtCustomer);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    static boolean isAirplaneModeOn(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        return Settings.System.getInt(contentResolver, AIRPLANE_MODE_ON, 0) != 0;
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    public void initParam(){
        Intent i = getIntent();
        nama = i.getStringExtra("NAMA");
        latitude = i.getStringExtra("LATITUDE");
        longitude = i.getStringExtra("LONGITUDE");

        nama_outlet = i.getStringExtra("NAMAOUTLET");
        latitude_outlet = i.getStringExtra("LATITUDEOUTLET");
        longitude_outlet = i.getStringExtra("LONGITUDEOUTLET");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        start = new LatLng(Double.valueOf(latitude_outlet), Double.valueOf(longitude_outlet));
        end = new LatLng(Double.valueOf(latitude),Double.valueOf(longitude));
        Navigator nav = new Navigator(mMap,start,end,getApplicationContext(), this);
        nav.findDirections(true, false);

        addMarker(start, nama_outlet, bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_marker_outlet));
        addMarker(end, nama, bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_marker_customer));
        zoomMaps();
        initInfo();
    }
    private void initInfo(){
        txtOutlet.setText(nama_outlet);
        txtCustomer.setText(nama);
    }
    private void addMarker(LatLng latLng, String caption, BitmapDescriptor drawable){
        mMap.addMarker(new MarkerOptions()
                .icon(drawable)
                .position(latLng)
                .title(caption));
    }

    public void zoomMaps(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(start);
        builder.include(end);

        LatLngBounds latLngBounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.30);

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, padding);

        mMap.animateCamera(cu);
    }
}

package illiyin.mhandharbeni.burgertahudelivery.fragment.sub;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.databasemodule.ModelOutlet;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmResults;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ModelOutlet modelOutlet;
    Crud crudOutlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        modelOutlet = new ModelOutlet();
        crudOutlet = new Crud(this, modelOutlet);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        listMarker();
    }

    public void listMarker(){
        RealmResults results = crudOutlet.read();
        if (results.size() > 0){
            for (int i=0;i<results.size();i++){
                ModelOutlet outlet = (ModelOutlet) results.get(i);
                Double dLat = Double.valueOf(outlet.getLatitude());
                Double dLong = Double.valueOf(outlet.getLongitude());
                LatLng latLng = new LatLng(dLat, dLong);
                mMap.addMarker(new MarkerOptions().position(latLng).title(outlet.getOutlet()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera( CameraUpdateFactory.zoomTo( 11.0f ) );
            }
        }
    }
}

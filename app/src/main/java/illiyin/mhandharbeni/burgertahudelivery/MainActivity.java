package illiyin.mhandharbeni.burgertahudelivery;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import illiyin.mhandharbeni.burgertahudelivery.fragment.FragmentFavorite;
import illiyin.mhandharbeni.burgertahudelivery.fragment.FragmentHome;
import illiyin.mhandharbeni.burgertahudelivery.fragment.FragmentMenu;
import illiyin.mhandharbeni.burgertahudelivery.fragment.FragmentOther;
import illiyin.mhandharbeni.burgertahudelivery.fragment.FragmentTrack;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.Cart;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.Login;
import illiyin.mhandharbeni.burgertahudelivery.utils.BottomNavigationViewHelper;
import illiyin.mhandharbeni.servicemodule.service.MainService;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;

public class MainActivity extends AppCompatActivity implements SessionListener {

    Session session;

    private TextView mTextMessage;
    private Fragment fragment;
    BottomNavigationView navigation;
    private TextView cartx;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new FragmentHome();
                    changeFragment(fragment);
                    return true;
                case R.id.navigation_menu:
                    fragment = new FragmentMenu();
                    changeFragment(fragment);
                    return true;
                case R.id.navigation_favorite:
                    fragment = new FragmentFavorite();
                    changeFragment(fragment);
                    return true;
                case R.id.navigation_track:
                    fragment = new FragmentTrack();
                    changeFragment(fragment);
//                    checkSession();
                    return true;
                case R.id.navigation_other:
                    fragment = new FragmentOther();
                    changeFragment(fragment);
//                    checkSession();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new Session(getApplicationContext(), this);
        runServices();
        setContentView(R.layout.activity_main);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        cartx = (TextView) findViewById(R.id.cartx);
        cartx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!session.checkSession()){
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    int requesCode = 2;
                    startActivityForResult(i, requesCode);
                }else{
                    Intent i = new Intent(getApplicationContext(), Cart.class);
                    int requestCode = 1;
                    startActivityForResult(i, requestCode);
                }
            }
        });

        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        changeSelectedMenu(0);
    }

    private void checkSession(){
        if (!session.checkSession()){
            Intent i = new Intent(this, Login.class);
            int requesCode = 2;
            startActivityForResult(i, requesCode);
        }
    }

    private void changeFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
    }

    @Override
    public void sessionChange() {
    }

    private void runServices(){
        if (!checkIsRunning(MainService.class)){
            Intent i = new Intent(this, MainService.class);
            startService(i);
        }
    }
    private boolean checkIsRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public void changeSelectedMenu(Integer position){
        int id = R.id.navigation_home;
        switch (position){
            case 0:
                id = R.id.navigation_home;
                break;
            case 1:
                id = R.id.navigation_menu;
                break;
            case 2:
                id = R.id.navigation_favorite;
                break;
            case 3:
                id = R.id.navigation_track;
                break;
            case 4:
                id = R.id.navigation_other;
                break;
        }

        View view = navigation.findViewById(id);
        view.performClick();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            changeSelectedMenu(1);
        }else if (requestCode == 2){
            changeSelectedMenu(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                Intent i = new Intent(this, Cart.class);
                int requestCode = 1;
                startActivityForResult(i, requestCode);
                break;
            default:
                break;
        }

        return true;
    }
}

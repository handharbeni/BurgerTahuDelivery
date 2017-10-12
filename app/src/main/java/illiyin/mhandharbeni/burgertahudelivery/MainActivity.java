package illiyin.mhandharbeni.burgertahudelivery;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import illiyin.mhandharbeni.burgertahudelivery.adapter.TabsPagerAdapter;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.Cart;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.Login;
import illiyin.mhandharbeni.servicemodule.service.MainService;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.utilslibrary.PermissionUtil;

public class MainActivity extends AppCompatActivity implements SessionListener {

    Session session;

    private TextView cartx;
    private ImageView flagen, flagind;
    private TabsPagerAdapter mAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permissions = new String[11];
        permissions[0] = Manifest.permission.CAMERA;
        permissions[1] = Manifest.permission.INTERNET;
        permissions[2] = Manifest.permission.WAKE_LOCK;
        permissions[3] = Manifest.permission.LOCATION_HARDWARE;
        permissions[4] = Manifest.permission.ACCESS_COARSE_LOCATION;
        permissions[5] = Manifest.permission.ACCESS_FINE_LOCATION;
        permissions[6] = Manifest.permission.READ_PHONE_STATE;
        permissions[7] = Manifest.permission.ACCESS_NETWORK_STATE;
        permissions[8] = Manifest.permission.ACCESS_WIFI_STATE;
        permissions[9] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        permissions[10] = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    5
            );
        }
        session = new Session(getApplicationContext(), this);
        runServices();
        String lang = session.getCustomParams("LANGUAGE", "en");
        String languageToLoad  = lang;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_main);


        flagen = (ImageView) findViewById(R.id.flagen);
        flagind = (ImageView) findViewById(R.id.flagind);

        flagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setCustomParams("LANGUAGE", "en");
                finish();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        flagind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setCustomParams("LANGUAGE", "in");
                finish();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                Toast.makeText(MainActivity.this, "Mohon Restart Aplikasi", Toast.LENGTH_SHORT).show();
            }
        });

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
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager = (ViewPager) findViewById(R.id.content);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_menu);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_favorite);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_track);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_other_black);
//        tabLayout.getTabAt(0).setIcon(R.drawable.newest_home);
//        tabLayout.getTabAt(1).setIcon(R.drawable.newest_menu);
//        tabLayout.getTabAt(2).setIcon(R.drawable.newest_fav);
//        tabLayout.getTabAt(3).setIcon(R.drawable.newest_track);
//        tabLayout.getTabAt(4).setIcon(R.drawable.newest_other);
//
//        TabLayout.Tab tabHome = tabLayout.getTabAt(0);tele
//        View tabView = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
//        tabView.requestLayout();
//        View view = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        ImageView iconHome = (ImageView) view.findViewById(R.id.icon);
//        tabHome.setCustomView(view);
//        tabHome.setText("");
//        if (lang.equalsIgnoreCase("en")){
//            Picasso.with(this).load(R.drawable.new_home).into(iconHome);
//        }else{
//            Picasso.with(this).load(R.drawable.new_home).into(iconHome);
//        }
//
//        TabLayout.Tab tabMenu = tabLayout.getTabAt(1);
//        View tabViewMenu = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
//        tabViewMenu.requestLayout();
//        View viewMenu = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        ImageView iconMenu = (ImageView) viewMenu.findViewById(R.id.icon);
//        tabMenu.setCustomView(viewMenu);
//        tabMenu.setText("");
//        if (lang.equalsIgnoreCase("en")){
//            Picasso.with(this).load(R.drawable.new_menu).into(iconMenu);
//        }else{
//            Picasso.with(this).load(R.drawable.new_menu).into(iconMenu);
//        }
//
//        TabLayout.Tab tabFavorite = tabLayout.getTabAt(2);
//        View tabViewFavorite = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
//        tabViewFavorite.requestLayout();
//        View viewFavorite = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        ImageView iconFavorite = (ImageView) viewFavorite.findViewById(R.id.icon);
//        tabFavorite.setCustomView(viewFavorite);
//        tabFavorite.setText("");
//        if (lang.equalsIgnoreCase("en")){
//            Picasso.with(this).load(R.drawable.new_fav_eng).into(iconFavorite);
//        }else{
//            Picasso.with(this).load(R.drawable.new_fav_indo).into(iconFavorite);
//        }
//
//        TabLayout.Tab tabTrack = tabLayout.getTabAt(3);
//        View tabViewTrack = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
//        tabViewTrack.requestLayout();
//        View viewTrack = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        ImageView iconTrack = (ImageView) viewTrack.findViewById(R.id.icon);
//        tabTrack.setCustomView(viewTrack);
//        tabTrack.setText("");
//        if (lang.equalsIgnoreCase("en")){
//            Picasso.with(this).load(R.drawable.new_cek_indo).into(iconTrack);
//        }else{
//            Picasso.with(this).load(R.drawable.new_cek_indo).into(iconTrack);
//        }
//
//        TabLayout.Tab tabOther = tabLayout.getTabAt(4);
//        View tabViewOther = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
//        tabViewOther.requestLayout();
//        View viewOther = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        ImageView iconOther = (ImageView) viewOther.findViewById(R.id.icon);
//        tabOther.setCustomView(viewOther);
//        tabOther.setText("");
//        if (lang.equalsIgnoreCase("en")){
//            Picasso.with(this).load(R.drawable.new_lain_eng).into(iconOther);
//        }else{
//            Picasso.with(this).load(R.drawable.new_lain_indo).into(iconOther);
//        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
        TabLayout.Tab newTab = tabLayout.getTabAt(position);
        newTab.select();
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

    @Override
    public void onBackPressed() {
        if (tabLayout.getSelectedTabPosition() != 0){
            changeSelectedMenu(0);
        }else{
//            super.onBackPressed();
            finish();
        }
    }
}

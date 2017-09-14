package illiyin.mhandharbeni.burgertahudelivery.fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import illiyin.mhandharbeni.burgertahudelivery.MainActivity;
import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.Cart;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.Login;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.MapsActivity;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;

/**
 * Created by root on 8/26/17.
 */

public class FragmentHome extends Fragment implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener, SessionListener {
    View v;
    private SliderLayout sliderBanner;
    private ImageView menu, order, outlet;
    private Session session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new Session(getActivity().getApplicationContext(), this);

        v = inflater.inflate(R.layout._layouthome, container, false);

        menu = (ImageView) v.findViewById(R.id.menu);
        order = (ImageView) v.findViewById(R.id.order);
        outlet = (ImageView) v.findViewById(R.id.outlet);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeSelectedMenu(1);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!session.checkSession()){
                    Intent i = new Intent(getActivity().getApplicationContext(), Login.class);
                    int requesCode = 2;
                    startActivityForResult(i, requesCode);
                }else{
                    Intent i = new Intent(getActivity().getApplicationContext(), Cart.class);
                    int requestCode = 1;
                    startActivityForResult(i, requestCode);
                }
            }
        });
        outlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), MapsActivity.class);
                startActivity(i);
            }
        });


        sliderBanner = (SliderLayout) v.findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Burger Jowo, Eco tur Nglegeno",R.drawable.slide_1);
        file_maps.put("Daging tebal berkualitas diapit dua lapis tahu tebal yang renyah",R.drawable.slide_2);
        file_maps.put("Tentukan alamat anda lalu tunggu kurir kami sampai depan rumah anda",R.drawable.slide_3);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity().getApplicationContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderBanner.addSlider(textSliderView);
        }
        sliderBanner.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderBanner.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderBanner.setCustomAnimation(new DescriptionAnimation());
        sliderBanner.setDuration(4000);
        sliderBanner.addOnPageChangeListener(this);
        return v;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void sessionChange() {

    }
}
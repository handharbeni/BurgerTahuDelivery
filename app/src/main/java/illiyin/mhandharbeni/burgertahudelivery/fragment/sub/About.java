package illiyin.mhandharbeni.burgertahudelivery.fragment.sub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import illiyin.mhandharbeni.burgertahudelivery.R;
import mehdi.sakout.aboutpage.AboutPage;

/**
 * Created by root on 9/8/17.
 */

public class About extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.logo)
                .setDescription(getString(R.string.descabout))
//                        .addItem("")
//                        .addItem(adsElement)
                .addGroup(getString(R.string.about))
//                        .addEmail("elmehdi.sakout@gmail.com")
                .addWebsite("http://burgertahumalang.com")
//                        .addFacebook("the.medy")
//                        .addTwitter("medyo80")
//                        .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
//                        .addPlayStore("com.ideashower.readitlater.pro")
//                        .addGitHub("medyo")
//                        .addInstagram("medyo80")
                .create();
        setContentView(v);
    }
}

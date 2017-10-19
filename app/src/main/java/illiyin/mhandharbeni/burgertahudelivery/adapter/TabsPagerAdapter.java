package illiyin.mhandharbeni.burgertahudelivery.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.burgertahudelivery.fragment.FragmentFavorite;
import illiyin.mhandharbeni.burgertahudelivery.fragment.FragmentHome;
import illiyin.mhandharbeni.burgertahudelivery.fragment.FragmentMenu;
import illiyin.mhandharbeni.burgertahudelivery.fragment.FragmentOther;
import illiyin.mhandharbeni.burgertahudelivery.fragment.FragmentTrack;

/**
 * Created by root on 20/07/17.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public TabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentMenu();
            case 2:
                return new FragmentFavorite();
            case 3:
                return new FragmentTrack();
            case 4:
                return new FragmentOther();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
//        if (position == 0)
//        {
//            title = "";
//        }
//        else if (position == 1)
//        {
//            title = mContext.getString(R.string.title_menu);
//        }
//        else if (position == 2)
//        {
//            title = mContext.getString(R.string.title_favorite);
//        }
//        else if (position == 3)
//        {
//            title = mContext.getString(R.string.title_track);
//        }
//        else if (position == 4)
//        {
//            title = mContext.getString(R.string.title_other);
//        }
        return title;
    }
}
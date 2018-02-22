package illiyin.mhandharbeni.burgertahudelivery.fragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.About;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.Login;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.Offers;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.OrderHistory;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import mehdi.sakout.aboutpage.AboutPage;

/**
 * Created by root on 8/26/17.
 */

public class FragmentOther extends Fragment implements SessionListener {
    View v;
    private TextView about, myaccount, addressbook, logout, orderhistory, offers, signintextview;
    private TextView headerorder, headeraccount, headerabout;
    private Session session;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        v = inflater.inflate(R.layout._layoutother, container, false);
        session = new Session(getActivity().getApplicationContext(), this);

        if (session.checkSession()){
            setFont(orderhistory, R.id.orderhistory, "twcen_condensed_extra_bold.ttf", v);
            setFont(about, R.id.about, "twcen_condensed_extra_bold.ttf", v);
            setFont(myaccount, R.id.myaccount, "twcen_condensed_extra_bold.ttf", v);
            setFont(addressbook, R.id.addressbook, "twcen_condensed_extra_bold.ttf", v);
            setFont(logout, R.id.logout, "twcen_condensed_extra_bold.ttf", v);
            setFont(offers, R.id.offers, "twcen_condensed_extra_bold.ttf", v);
            setFont(signintextview, R.id.signintextview, "twcen_condensed_extra_bold.ttf", v);

            setFont(headerorder, R.id.headerorder, "twcen_condensed.ttf", v);
            setFont(headeraccount, R.id.headeraccount, "twcen_condensed.ttf", v);
            setFont(headerabout, R.id.headerabout, "twcen_condensed.ttf", v);

            castElementTextView(myaccount, R.id.myaccount, v).setVisibility(View.VISIBLE);
            castElementTextView(addressbook, R.id.addressbook, v).setVisibility(View.VISIBLE);
            castElementTextView(logout, R.id.logout, v).setVisibility(View.VISIBLE);
            castElementTextView(orderhistory, R.id.orderhistory, v).setVisibility(View.VISIBLE);
            castElementTextView(signintextview, R.id.signintextview, v).setVisibility(View.GONE);

            castElementTextView(myaccount, R.id.myaccount, v).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkSession();
                }
            });
            castElementTextView(addressbook, R.id.addressbook, v).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            castElementTextView(logout, R.id.logout, v).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    session.deleteSession();
                    onCreateView(inflater, container, savedInstanceState);
                }
            });
            castElementTextView(orderhistory, R.id.orderhistory, v).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity().getApplicationContext(), OrderHistory.class);
                    startActivity(i);
                }
            });

        }else{
            setFont(orderhistory, R.id.orderhistory, "twcen_condensed_extra_bold.ttf", v);
            setFont(about, R.id.about, "twcen_condensed_extra_bold.ttf", v);
            setFont(myaccount, R.id.myaccount, "twcen_condensed_extra_bold.ttf", v);
            setFont(addressbook, R.id.addressbook, "twcen_condensed_extra_bold.ttf", v);
            setFont(logout, R.id.logout, "twcen_condensed_extra_bold.ttf", v);
            setFont(offers, R.id.offers, "twcen_condensed_extra_bold.ttf", v);
            setFont(signintextview, R.id.signintextview, "twcen_condensed_extra_bold.ttf", v);

            setFont(headerorder, R.id.headerorder, "twcen_condensed.ttf", v);
            setFont(headeraccount, R.id.headeraccount, "twcen_condensed.ttf", v);
            setFont(headerabout, R.id.headerabout, "twcen_condensed.ttf", v);

            castElementTextView(myaccount, R.id.myaccount, v).setVisibility(View.GONE);
            castElementTextView(addressbook, R.id.addressbook, v).setVisibility(View.GONE);
            castElementTextView(logout, R.id.logout, v).setVisibility(View.GONE);
            castElementTextView(orderhistory, R.id.orderhistory, v).setVisibility(View.GONE);
            castElementTextView(signintextview, R.id.signintextview, v).setVisibility(View.VISIBLE);
            castElementTextView(signintextview, R.id.signintextview, v).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity().getApplicationContext(), Login.class);
                    int requesCode = 2;
                    startActivityForResult(i, requesCode);
                }
            });
        }

        castElementTextView(about, R.id.about, v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), About.class);
                startActivity(i);
            }
        });
        castElementTextView(offers, R.id.offers, v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), Offers.class);
                startActivity(i);
            }
        });

        return v;
    }
    private void setFont(TextView name, int id, String font, View parent){
        name = (TextView) parent.findViewById(id);
        name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), font));
    }
    private View castElementTextView(View name, int id, View parent){
        return name = (TextView) parent.findViewById(id);
    }
    private void checkSession(){
        if (!session.checkSession()){
            Intent i = new Intent(getActivity().getApplicationContext(), Login.class);
            int requesCode = 2;
            startActivityForResult(i, requesCode);
        }
    }

    @Override
    public void sessionChange() {

    }
}

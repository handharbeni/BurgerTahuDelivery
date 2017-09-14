package illiyin.mhandharbeni.burgertahudelivery.fragment;

import android.content.Intent;
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
    private Session session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout._layoutother, container, false);
        session = new Session(getActivity().getApplicationContext(), this);

        if (session.checkSession()){
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

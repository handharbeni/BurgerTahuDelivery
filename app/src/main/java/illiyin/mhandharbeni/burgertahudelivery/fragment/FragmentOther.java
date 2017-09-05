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
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.Login;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.OrderHistory;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;

/**
 * Created by root on 8/26/17.
 */

public class FragmentOther extends Fragment implements SessionListener {
    View v;
    private TextView about, myaccount, addressbook, logout, orderhistory, offers;
    private Session session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout._layoutother, container, false);
        session = new Session(getActivity().getApplicationContext(), this);
        castElementTextView(about, R.id.about, v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        castElementTextView(offers, R.id.offers, v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

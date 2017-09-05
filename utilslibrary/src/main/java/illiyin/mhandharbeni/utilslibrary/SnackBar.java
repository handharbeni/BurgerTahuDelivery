package illiyin.mhandharbeni.utilslibrary;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.golovin.fluentstackbar.FluentSnackbar;

/**
 * Created by root on 17/07/17.
 */

public class SnackBar {
    Context context;
    AppCompatActivity appCompatActivity;
    private FluentSnackbar mFluentSnackbar;

    public SnackBar(Context context, AppCompatActivity appCompatActivity) {
        this.context = context;
        this.appCompatActivity = appCompatActivity;
        mFluentSnackbar = FluentSnackbar.create(appCompatActivity);
    }

    public void show(String message){
        mFluentSnackbar.create(message)
                .maxLines(2)
                .backgroundColorRes(R.color.colorPrimary)
                .duration(Snackbar.LENGTH_SHORT)
                .important()
                .show();
    }
}

package illiyin.mhandharbeni.burgertahudelivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.burgertahudelivery.fragment.sub.DetailMenu;
import illiyin.mhandharbeni.databasemodule.ModelCart;
import illiyin.mhandharbeni.databasemodule.ModelMenu;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.utilslibrary.NumberFormat;
import io.realm.RealmResults;

/**
 * Created by root on 22/07/17.
 */

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MyViewHolder> {
    private String TAG = "MenuAdapter";
    private List<ModelMenu> menuList;
    private Context mContext;
    private ModelMenu modelMenu;
    private ModelCart modelCart;
    private Crud crudMenu;
    private Crud crudCart;
    private NumberFormat numberFormat;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title, price;
        public LinearLayout parentlayout;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
            price  = (TextView) view.findViewById(R.id.price);
            parentlayout = (LinearLayout) view.findViewById(R.id.parentlayout);
        }
    }

    public MenuItemAdapter(Context mContext, List<ModelMenu> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
        modelCart = new ModelCart();
        modelMenu = new ModelMenu();
        this.crudMenu = new Crud(mContext, modelMenu);
        this.crudCart = new Crud(mContext, modelCart);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ModelMenu m = menuList.get(position);
        Picasso.with(mContext).load(m.getGambar()).placeholder(R.mipmap.ic_launcher).into(holder.image);
        holder.title.setText(m.getNama());
        holder.price.setText(numberFormat.format(Double.valueOf(m.getHarga())));
        holder.parentlayout.setTag(m.getId());
        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailMenu.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("idMenu", Integer.valueOf(holder.parentlayout.getTag().toString()));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemmenu ,parent, false);
        return new MyViewHolder(v);
    }
    public void swap(List<ModelMenu> datas){
        menuList.clear();
        menuList.addAll(datas);
        notifyDataSetChanged();
    }
}
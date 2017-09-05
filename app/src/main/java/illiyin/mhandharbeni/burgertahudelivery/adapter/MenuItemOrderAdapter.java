package illiyin.mhandharbeni.burgertahudelivery.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.databasemodule.ItemOrder;
import illiyin.mhandharbeni.databasemodule.ModelCart;
import illiyin.mhandharbeni.realmlibrary.Crud;

/**
 * Created by root on 05/08/17.
 */

public class MenuItemOrderAdapter extends RecyclerView.Adapter<MenuItemOrderAdapter.MyViewHolder> {
    private String TAG = "MenuAdapter";
    private List<ItemOrder> menuList;
    private Context mContext;
    private ItemOrder itemOrder;
    private ModelCart modelCart;
    private Crud crudMenu;
    private Crud crudCart;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitleMenu, txtHargaMenu, eQty;
        public Button btnAddMenu;
        public ImageView imageMenu;

        public MyViewHolder(View view) {
            super(view);
//            txtTitleMenu = (TextView) view.findViewById(R.id.txtTitleMenu);
//            txtHargaMenu = (TextView) view.findViewById(R.id.txtHargaMenu);
//            btnAddMenu = (Button) view.findViewById(R.id.btnAddMenu);
//            eQty = (TextView) view.findViewById(R.id.eQty);
//            imageMenu = (ImageView) view.findViewById(R.id.imageMenu);
        }
    }

    public MenuItemOrderAdapter(Context mContext, List<ItemOrder> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
        modelCart = new ModelCart();
        itemOrder = new ItemOrder();
        this.crudMenu = new Crud(mContext, itemOrder);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final MenuItemOrderAdapter.MyViewHolder holder, int position) {
        final ItemOrder m = menuList.get(position);
        holder.txtTitleMenu.setText(m.getNama_menu());
        holder.txtHargaMenu.setText(m.getHarga());
        holder.eQty.setText(m.getJumlah());
        Picasso.with(mContext).load(m.getGambar()).placeholder(R.mipmap.ic_launcher).into(holder.imageMenu);

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    @Override
    public MenuItemOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemmenu ,parent, false);
        return new MenuItemOrderAdapter.MyViewHolder(v);
    }
    public void swap(List<ItemOrder> datas){
        menuList.clear();
        menuList.addAll(datas);
        notifyDataSetChanged();
    }
}

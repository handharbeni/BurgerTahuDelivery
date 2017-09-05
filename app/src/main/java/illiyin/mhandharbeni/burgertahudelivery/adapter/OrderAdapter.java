package illiyin.mhandharbeni.burgertahudelivery.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.burgertahudelivery.adapter.utils.CartUtil;
import illiyin.mhandharbeni.databasemodule.ModelCart;
import illiyin.mhandharbeni.databasemodule.ModelMenu;
import illiyin.mhandharbeni.databasemodule.ModelOrder;
import illiyin.mhandharbeni.realmlibrary.Crud;

/**
 * Created by root on 02/08/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private String TAG = "OrderAdapter";
    private List<ModelOrder> menuList;
    private Context mContext;
    private ModelMenu modelMenu;
    private ModelCart modelCart;
    private Crud crudMenu;
    private Crud crudCart;

    private CartUtil cartUtil;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idOrder, tujuanOrder, totalOrder, statusOrder, tanggalOrder;
        ImageView imgOrder;
        RelativeLayout rlItemOrder;
        public MyViewHolder(View view) {
            super(view);
            rlItemOrder = (RelativeLayout)view.findViewById(R.id.rlItemOrder);
            idOrder = (TextView)view.findViewById(R.id.idOrder);
            tujuanOrder = (TextView)view.findViewById(R.id.tujuanOrder);
            totalOrder = (TextView)view.findViewById(R.id.totalOrder);
            statusOrder = (TextView)view.findViewById(R.id.statusOrder);
            tanggalOrder = (TextView)view.findViewById(R.id.tanggalOrder);
            imgOrder = (ImageView)view.findViewById(R.id.imgOrder);
        }
    }

    public OrderAdapter(Context mContext, List<ModelOrder> menuList, CartUtil cartUtil) {
        this.mContext = mContext;
        this.menuList = menuList;
        modelCart = new ModelCart();
        modelMenu = new ModelMenu();
        this.crudMenu = new Crud(mContext, modelMenu);
        this.crudCart = new Crud(mContext, modelCart);
        this.cartUtil = cartUtil;
    }
    public OrderAdapter(Context mContext, List<ModelOrder> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
        modelCart = new ModelCart();
        modelMenu = new ModelMenu();
        this.crudMenu = new Crud(mContext, modelMenu);
        this.crudCart = new Crud(mContext, modelCart);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final OrderAdapter.MyViewHolder holder, int position) {
        final ModelOrder m = menuList.get(position);
        holder.idOrder.setText("#"+String.valueOf(m.getId()));
        holder.tujuanOrder.setText(m.getAlamat_customer());
        holder.totalOrder.setText(numberFormat(Double.valueOf(m.getTotal_belanja())));
        holder.statusOrder.setText(getStatus(m.getStatus()));
        holder.tanggalOrder.setText(m.getTanggal());
        holder.rlItemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cartUtil.changeActivity(String.valueOf(m.getId()));
            }
        });
    }
    private String getStatus(String mStatus){
        String status = "Order Baru";
        switch (mStatus){
            case "1":
                status = "Order Terkirim";
                break;
            case "2":
                status = "Order Diterima Outlet";
                break;
            case "3":
                status = "Order Diterima Kurir";
                break;
            case "4":
                status = "Order Sedang Dikirim Oleh Kurir";
                break;
            case "5":
                status = "Order Selesai";
                break;
            case "6":
                status = "Order Dibatalkan";
                break;
        }
        return status;
    }
    public static String numberFormat(double d) {
        Double value = d;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("Rp.");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(dfs);
        return formatter.format(value);
    }
    @Override
    public int getItemCount() {
        return menuList.size();
    }

    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemorder ,parent, false);
        return new OrderAdapter.MyViewHolder(v);
    }
    public void swap(List<ModelOrder> datas){
        menuList.clear();
        menuList.addAll(datas);
        notifyDataSetChanged();
    }
    public void clear() {
        notifyDataSetChanged();
    }
}
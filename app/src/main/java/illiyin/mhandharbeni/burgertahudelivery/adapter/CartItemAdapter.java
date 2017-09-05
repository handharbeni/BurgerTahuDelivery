package illiyin.mhandharbeni.burgertahudelivery.adapter;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrules.horizontalnumberpicker.HorizontalNumberPicker;
import com.hrules.horizontalnumberpicker.HorizontalNumberPickerListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.burgertahudelivery.adapter.utils.CartUtil;
import illiyin.mhandharbeni.databasemodule.ModelCart;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmResults;

/**
 * Created by root on 25/07/17.
 */

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.MyViewHolder> {
    private String TAG = "CartAdapter";
    private List<ModelCart> menuList;
    private Context mContext;
    private Crud crudCart;
    private ModelCart modelCart;

    private CartUtil cartUtil;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title, price;
        public View cartComponent;
        public HorizontalNumberPicker qty;
        public Button deleteCart;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
            price  = (TextView) view.findViewById(R.id.price);
            cartComponent = (View) view.findViewById(R.id.cartComponent);
            qty = (HorizontalNumberPicker) view.findViewById(R.id.qty);
            deleteCart = (Button) view.findViewById(R.id.deleteCart);
        }
    }

    public CartItemAdapter(Context mContext, List<ModelCart> menuList, CartUtil cartUtil) {
        this.mContext = mContext;
        this.menuList = menuList;
        this.modelCart = new ModelCart();
        this.crudCart = new Crud(this.mContext, this.modelCart);
        this.cartUtil = cartUtil;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final CartItemAdapter.MyViewHolder holder, int position) {
        if (position <= getItemCount()){
            final int fPosition = position;
            final ModelCart m = menuList.get(fPosition);

            if (!m.isValid()){
                return;
            }

            RealmResults menuResult = crudCart.read("id", m.getId());
            if (menuResult.size() > 0){
                ModelCart model = (ModelCart) menuResult.get(0);
                String nameMenu = model.getNama();
                String priceMenu = model.getHarga();
                String descMenu = model.getNama();
                String katMenu = model.getKategori();
                String gambar = model.getGambar();
                String title = nameMenu;
                String image = gambar;
                holder.cartComponent.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(image).placeholder(R.mipmap.ic_launcher).into(holder.image);
                holder.title.setText(nameMenu);
                holder.price.setText(priceMenu);
                holder.qty.setValue(m.getJumlah());
                holder.qty.setListener(new HorizontalNumberPickerListener() {
                    @Override
                    public void onHorizontalNumberPickerChanged(HorizontalNumberPicker horizontalNumberPicker, int value) {
                        if (value == 0){
                            deleteItemTemp(m.getId(), holder);
                        }else{
                            updateItemTemp(m.getId(), value);
                        }
                    }
                });
                holder.deleteCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItemTemp(m.getId(), holder);
                    }
                });
//                holder.txtTitleMenu.setText(title);
//                holder.txtHargaMenu.setText(String.valueOf(m.getHarga()));
//                holder.eQty.setText(String.valueOf(m.getJumlah()));
//                Picasso.with(mContext).load(image).placeholder(R.mipmap.ic_launcher).into(holder.imageMenu);
//                holder.eQty.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        if (s.length() > 0){
//                            if (s.toString().equalsIgnoreCase("0")){
//                                deleteItemTemp(m.getId(), holder);
//                            }else{
//                                updateItemTemp(m.getId(), Integer.valueOf(s.toString()));
//                            }
//                        }
//
//                    }
//                });
//                holder.btnDeleteItem.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        deleteItemTemp(m.getId(), holder);
//                    }
//                });

            }
        }
    }
    public void swap(List<ModelCart> datas){
        menuList.clear();
        menuList.addAll(datas);
        notifyDataSetChanged();
    }

    public void updateItemTemp(int id, int qty){
        crudCart.openObject();
        ModelCart cart = (ModelCart) crudCart.getRealmObject("id", id);
        cart.setJumlah(qty);
        crudCart.update(cart);
        crudCart.commitObject();
        cartUtil.updateInfo();
    }
    public void deleteItemTemp(int id, final CartItemAdapter.MyViewHolder holder){
        crudCart.delete("id", id);
        int newPosition = holder.getAdapterPosition();
        menuList.remove(newPosition);
        notifyItemRemoved(newPosition);
        notifyItemRangeChanged(newPosition, menuList.size());
        cartUtil.updateInfo();
    }
    public void deleteItemTemp(int id, int position){
        crudCart.delete("id", id);
        menuList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, menuList.size());
        cartUtil.updateInfo();
    }
    @Override
    public int getItemCount() {
        return menuList.size();
    }

    @Override
    public CartItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemmenu ,parent, false);
        return new CartItemAdapter.MyViewHolder(v);
    }
}
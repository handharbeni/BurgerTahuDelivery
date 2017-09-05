package illiyin.mhandharbeni.burgertahudelivery.fragment.sub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrules.horizontalnumberpicker.HorizontalNumberPicker;
import com.squareup.picasso.Picasso;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.databasemodule.ModelCart;
import illiyin.mhandharbeni.databasemodule.ModelMenu;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmResults;

/**
 * Created by root on 9/1/17.
 */

public class DetailMenu extends AppCompatActivity {
    ModelMenu modelMenu;
    Crud crud;
    Integer idMenu;

    TextView title;
    ImageView image;
    HorizontalNumberPicker qty;
    Button order;


    private ModelCart modelCart;
    private Crud crudCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        modelMenu = new ModelMenu();
        modelCart = new ModelCart();

        crud = new Crud(getApplicationContext(), modelMenu);
        crudCart = new Crud(getApplicationContext(), modelCart);

        idMenu = getIntent().getIntExtra("idMenu", 0);
        if (idMenu == 0){
            finish();
        }

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.itemdetail);

        title = (TextView) findViewById(R.id.title);
        image = (ImageView) findViewById(R.id.image);
        qty = (HorizontalNumberPicker) findViewById(R.id.qty);
        order = (Button) findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToCart();
            }
        });

        getDataDetail();

    }
    private void getDataDetail(){
        RealmResults results = crud.read("id", idMenu);
        if (results.size() > 0){
            ModelMenu menu = (ModelMenu) results.get(0);
            String sTitle = menu.getNama();
            String sGambar = menu.getGambar();
            String sPrice = menu.getHarga();
            Picasso.with(this).load(sGambar).placeholder(R.mipmap.ic_launcher).into(image);
            title.setText(sTitle);
        }else{
            finish();
        }
    }

    private void addItemToCart(){
        int iQty = 1;
        if (qty.getValue() != 0){
            iQty = qty.getValue();
        }
        Boolean duplicate = crudCart.checkDuplicate("id", idMenu);
        if (!duplicate){
            RealmResults results = crud.read("id", idMenu);
            if (results.size() > 0){
                ModelMenu menu = (ModelMenu) results.get(0);
                ModelCart newCart = new ModelCart();
                newCart.setId(menu.getId());
                newCart.setNama(menu.getNama());
                newCart.setGambar(menu.getGambar());
                newCart.setHarga(menu.getHarga());
                newCart.setKategori(menu.getKategori());
                newCart.setJumlah(iQty);
                newCart.setSha(menu.getSha());
                crudCart.create(newCart);
            }
        }else{
            int lastQty = iQty;
            RealmResults results = crudCart.read("id", idMenu);
            ModelCart lastModelCart = (ModelCart) results.get(0);
            lastQty += lastModelCart.getJumlah();
            RealmResults resultz = crud.read("id", idMenu);
            if (results.size() > 0){
                ModelMenu menu = (ModelMenu) resultz.get(0);
                crudCart.openObject();
                ModelCart updateObject = (ModelCart) crudCart.getRealmObject("id", idMenu);
                updateObject.setSha(menu.getSha());
                updateObject.setKategori(menu.getKategori());
                updateObject.setHarga(menu.getHarga());
                updateObject.setGambar(menu.getGambar());
                updateObject.setJumlah(lastQty);
                updateObject.setNama(menu.getNama());
                crudCart.update(updateObject);
                crudCart.commitObject();
            }
        }

        Toast.makeText(this, "Berhasil ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();
    }

    private void rateMenu(){
//        token
//        method
//        id_menu
//        rating

    }
}

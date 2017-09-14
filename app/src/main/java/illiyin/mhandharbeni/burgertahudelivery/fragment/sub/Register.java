package illiyin.mhandharbeni.burgertahudelivery.fragment.sub;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.utilslibrary.SnackBar;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by root on 8/31/17.
 */

public class Register extends AppCompatActivity {
    CallHttp callHttp;

    EditText input_nama, input_email, input_nohp, input_password;
    Button btn_signup;
    TextView link_login;
    String endpoint = "/users/daftar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        callHttp = new CallHttp(getApplicationContext());
        setContentView(R.layout._layoutregister);
        input_nama = (EditText) findViewById(R.id.input_name);
        input_email = (EditText) findViewById(R.id.input_email);
        input_nohp = (EditText) findViewById(R.id.input_nohp);
        input_password = (EditText) findViewById(R.id.input_nohp);

        link_login = (TextView) findViewById(R.id.link_login);
        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogin();
                finish();
            }
        });

        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
    }
    public void doRegister(){
        String headServer = getString(R.string.module_server);
        headServer = headServer+endpoint;
        String sNama = input_nama.getText().toString();
        String sEmail = input_email.getText().toString();
        String sNoHp = input_nohp.getText().toString();
        String sPassword = input_password.getText().toString();

        if (!sNama.isEmpty() || !sEmail.isEmpty() || !sNoHp.isEmpty() || !sPassword.isEmpty()){
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("nama", sNama)
                    .addFormDataPart("email", sEmail)
                    .addFormDataPart("no_hp", sNoHp)
                    .addFormDataPart("password", sPassword)
                    .build();
            String response = callHttp.post(headServer, requestBody);
            try {
                JSONObject jsonObject = new JSONObject(response);
                Boolean returns = jsonObject.getBoolean("return");
                if (returns){
                    showLogin();
                    finish();
                }else{
                    SnackBar snackBar = new SnackBar(getApplicationContext(), this);
                    snackBar.show("Registrasi Gagal");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            if (sNama.isEmpty()){
                input_nama.setError("Tidak Boleh Kosong");
            }else if(sEmail.isEmpty()){
                input_email.setError("Tidak Boleh Kosong");
            }else if(sNoHp.isEmpty()){
                input_nohp.setError("Tidak Boleh Kosong");
            }else if(sPassword.isEmpty()){
                input_password.setError("Tidak Boleh Kosong");
            }
        }
    }
    public void showLogin(){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
}

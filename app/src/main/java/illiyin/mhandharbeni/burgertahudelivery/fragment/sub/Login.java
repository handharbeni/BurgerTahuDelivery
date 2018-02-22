package illiyin.mhandharbeni.burgertahudelivery.fragment.sub;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import illiyin.mhandharbeni.burgertahudelivery.R;
import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.utilslibrary.SnackBar;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by root on 8/31/17.
 */

public class Login extends AppCompatActivity implements SessionListener {
    private static final String TAG = "LoginActivity";
    Session session;
    CallHttp callHttp;

    EditText input_email, input_password;
    Button btn_login;
    TextView link_signup;

    String endpoint = "/users/login";
//    String endpointregister = "/users/daftar";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        session = new Session(getApplicationContext(), this);
        callHttp = new CallHttp(getApplicationContext());
        setContentView(R.layout._layoutlogin);

        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);

        link_signup = (TextView) findViewById(R.id.link_signup);
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegister();
                finish();
            }
        });

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }
    public void doLogin(){
        String headServer = getString(R.string.module_server);
        headServer = headServer+endpoint;
        String sEmail = input_email.getText().toString();
        String sPassword = input_password.getText().toString();
        if (!sEmail.isEmpty() || !sPassword.isEmpty()){
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("email", sEmail)
                    .addFormDataPart("password", sPassword)
                    .build();
            String response = callHttp.post(headServer, requestBody);
            Log.d(TAG, "doLogin: "+response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                Boolean returns = jsonObject.getBoolean("return");
                if (returns){
                    String nama = jsonObject.getString("nama");
                    String email = jsonObject.getString("email");
                    String key = jsonObject.getString("access_token");
                    session.setSession(nama, "", "", email, key, "1");
                }else{
                    //{"return":false,"error_message":"Email atau password salah!"}
                    SnackBar snackBar = new SnackBar(getApplicationContext(), this);
                    snackBar.show(jsonObject.getString("error_message"));
                    showToast(jsonObject.getString("error_message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            if (sEmail.isEmpty()){
                input_email.setError("Tidak Boleh Kosong");
            }else{
                input_password.setError("Tidak Boleh Kosong");
            }
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sessionChange() {
        if (session.checkSession()){
            /*login berhasil*/
            finish();
        }
    }
    public void showRegister(){
        Intent i = new Intent(this, Register.class);
        startActivity(i);
    }
}

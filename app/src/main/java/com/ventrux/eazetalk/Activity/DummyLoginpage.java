package com.ventrux.eazetalk.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ventrux.eazetalk.Adaptor.AstrologerListAdaptor;
import com.ventrux.eazetalk.Api.ApiLinks;
import com.ventrux.eazetalk.Firebase.Config;
import com.ventrux.eazetalk.Api.Configss;
import com.ventrux.eazetalk.Api.StaticData;
import com.ventrux.eazetalk.MainActivity;
import com.ventrux.eazetalk.R;
import com.ventrux.eazetalk.SplashScreen;
import com.ventrux.eazetalk.model.AstrologerModel;
import com.ventrux.eazetalk.response.AstrologerResponse;
import com.ventrux.eazetalk.response.LoginResponse;
import com.ventrux.eazetalk.service.CountryService;

import java.util.List;

public class DummyLoginpage extends AppCompatActivity {
EditText txtuserid;
Button send;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_loginpage);
        txtuserid=findViewById(R.id.txt_pin_entry);
        send=findViewById(R.id.sendotp);
        progressDialog=new ProgressDialog(DummyLoginpage.this);
        progressDialog.setMessage("Please wait while Loading ...");
        progressDialog.setCancelable(false);

        FirebaseApp.initializeApp(DummyLoginpage.this);
        FirebaseUser user=mAuth.getInstance().getCurrentUser();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtuserid.getText().toString().isEmpty()){
                    Toast.makeText(DummyLoginpage.this, "Enter Userid", Toast.LENGTH_SHORT).show();
                }
                else{
                loginfun();
            }}
        });
    }
    void loginfun(){
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        //Fetching the boolean value form sharedpreferences
       String  tokencode = sharedPreferences.getString("regId", "default");
        new CountryService().getAPI1().login(ApiLinks.login, txtuserid.getText().toString(),tokencode)
                .enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body()!=null){
                    progressDialog.dismiss();
                    if (response.body().getStatus().equals("true")){
                        Log.d("response", response.body().toString());
                        LoginResponse loginResponse = response.body();
                        //List<Profile> profiles = loginResponse.getData();
                        Log.d("accesstoken",response.body().getData().get(0).getFirebaseToken());
                        SharedPreferences sharedPreferences =getSharedPreferences
                                (Configss.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //Adding values to editor
                        editor.putBoolean(Configss.LOGGEDIN_SHARED_PREF, true);
                        //  editor.putString(Config.login_role,role);
                        editor.putString(Configss.login_role,"0");
                        editor.putString(Configss.tokencode,response.body().getData().get(0).getFirebaseToken());
                        editor.commit();
                        Intent intent = new Intent(DummyLoginpage.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(DummyLoginpage.this, "faild", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DummyLoginpage.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

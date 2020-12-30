package com.ventrux.eazetalk.Activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ventrux.eazetalk.Api.StaticData;
import com.ventrux.eazetalk.R;
import com.ventrux.eazetalk.response.LoginResponse;
import com.ventrux.eazetalk.service.CountryService;

public class AstrologerDetailsActivity extends AppCompatActivity {
    TextView name,language,chatprice,voiceprice,videoprice,aboutus;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astrologer_details);
        progressDialog=new ProgressDialog(AstrologerDetailsActivity.this);
        progressDialog.setMessage("Please wait while Loading ...");
        progressDialog.setCancelable(false);
        name=findViewById(R.id.astroname);
        language=findViewById(R.id.astrolanguage);
        chatprice=findViewById(R.id.chatprice);
        voiceprice=findViewById(R.id.videocallprice);
        videoprice=findViewById(R.id.videocallprice);
        aboutus=findViewById(R.id.aboutus);

        name.setText(StaticData.singleastrologer.getName());
        language.setText(StaticData.singleastrologer.getLanguage());
        chatprice.setText(StaticData.singleastrologer.getChatPrice());
        voiceprice.setText(StaticData.singleastrologer.getVoiceCallPrice());
        videoprice.setText(StaticData.singleastrologer.getVideoCallPrice());
        aboutus.setText(StaticData.singleastrologer.getAboutMe());
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title=findViewById(R.id.title);
        title.setText(StaticData.singleastrologer.getName());
        String tokencode="f8OWPZS2SBaCKoKNRr8Yg2:APA91bEE8LUsE1JGtXPaN_TzOb4Vwb_dn6Y3UYUTg8-cjvw3VgxYQxgH_uF36k23f7ffSdkfcPrO6pHpPuvafLWWCrzRu-UWIPUjWY-wYIBsmwg7HAdgaJ7iYESUPHH1PnDU_iw_Q4ci";
        findViewById(R.id.videocall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.callerstatus=0;
                getastrologerid();
               // pushnotiification("Calling ","Someone is call recieve call",tokencode);
                startActivity(new Intent(getApplicationContext(),VideoCallActivity.class));
            }
        });
    }
    void getastrologerid(){
        new CountryService().getAPI1().getfirebasetokencode("",StaticData.singleastrologer.getId()).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body()!=null){
                    progressDialog.dismiss();
                    if (response.body().getStatus().equals("true")){

                    }
                    else{
                        Toast.makeText(AstrologerDetailsActivity.this, "faild", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}

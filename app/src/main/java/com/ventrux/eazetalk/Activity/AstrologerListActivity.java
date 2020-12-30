package com.ventrux.eazetalk.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ventrux.eazetalk.Adaptor.AstrologerListAdaptor;
import com.ventrux.eazetalk.Api.ApiLinks;
import com.ventrux.eazetalk.Api.StaticData;
import com.ventrux.eazetalk.R;
import com.ventrux.eazetalk.model.AstrologerModel;
import com.ventrux.eazetalk.response.AstrologerResponse;
import com.ventrux.eazetalk.service.CountryService;

import org.w3c.dom.Text;

import java.util.List;

public class AstrologerListActivity extends AppCompatActivity {
    RecyclerView chapterrecycler;
    AstrologerListAdaptor catagorylistAdaptor;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astrologer_list);
        progressDialog=new ProgressDialog(AstrologerListActivity.this);

        progressDialog.setMessage("Please wait while Loading ...");
        progressDialog.setCancelable(false);
        chapterrecycler=findViewById(R.id.recyclerview);
        chapterrecycler.setHasFixedSize(true);
        chapterrecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        getservice();

    }
    void getservice(){
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        progressDialog.show();
        new CountryService().getAPI1().astrologerlist(ApiLinks.productlist, StaticData.cat_id).enqueue(new Callback<AstrologerResponse>() {
            @Override
            public void onResponse(Call<AstrologerResponse> call, Response<AstrologerResponse> response) {
                if (response.body()!=null){
                    progressDialog.dismiss();
                    if (response.body().getStatus().equals("true")){
                        List<AstrologerModel> servicelists=response.body().getData();
                        catagorylistAdaptor = new AstrologerListAdaptor(getApplicationContext(), servicelists);
                        chapterrecycler.setAdapter(catagorylistAdaptor);
                    }
                    else{
                        Toast.makeText(AstrologerListActivity.this, "faild", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AstrologerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AstrologerListActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

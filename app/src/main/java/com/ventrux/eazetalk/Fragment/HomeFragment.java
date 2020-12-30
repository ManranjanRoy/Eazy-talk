package com.ventrux.eazetalk.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ventrux.eazetalk.Activity.AstrologerDetailsActivity;
import com.ventrux.eazetalk.Activity.AstrologerListActivity;
import com.ventrux.eazetalk.Adaptor.AstrologerListAdaptor;
import com.ventrux.eazetalk.Api.ApiLinks;
import com.ventrux.eazetalk.Api.StaticData;
import com.ventrux.eazetalk.R;
import com.ventrux.eazetalk.model.AstrologerModel;
import com.ventrux.eazetalk.response.AstrologerResponse;
import com.ventrux.eazetalk.service.CountryService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    List<Slide> slideList;
    Slider slider;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout astrologer,doctor;
    RecyclerView chapterrecycler;
    AstrologerListAdaptor catagorylistAdaptor;
    ProgressDialog progressDialog;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        init(v);
        return  v;
    }

    private void init(View v) {
        slider = v.findViewById(R.id.slider);
        slideList = new ArrayList<>();
        astrologer= v.findViewById(R.id.astrologer);
        doctor= v.findViewById(R.id.doctor);
        slideList.add(new Slide(1,"https://image.shutterstock.com/z/stock-vector-vector-illustration-of-sacred-geometric-symbol-against-the-space-background-with-sunrise-and-stars-1676168917.jpg",0));
        slideList.add(new Slide(1,"https://image.shutterstock.com/z/stock-vector-vector-illustration-of-sacred-geometric-symbol-against-the-space-background-with-sunrise-and-stars-1676168917.jpg",0));
        slideList.add(new Slide(1,"https://image.shutterstock.com/z/stock-vector-vector-illustration-of-sacred-geometric-symbol-against-the-space-background-with-sunrise-and-stars-1676168917.jpg",0));
        slider.addSlides(slideList);
        progressDialog=new ProgressDialog(getContext());

        progressDialog.setMessage("Please wait while Loading ...");
        progressDialog.setCancelable(false);
        chapterrecycler=v.findViewById(R.id.recyclerview);
        chapterrecycler.setHasFixedSize(true);
        chapterrecycler.setLayoutManager(new GridLayoutManager(getContext(),2));

        getservice();
        swipeRefreshLayout=v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
            }
        });
       astrologer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.cat_id="12";
                startActivity(new Intent(getContext(), AstrologerListActivity.class));
            }
        });
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.cat_id="13";
                startActivity(new Intent(getContext(), AstrologerListActivity.class));
            }
        });
      /*  v.findViewById(R.id.a1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AstrologerDetailsActivity.class));
            }
        });*/
    }
    void getservice(){
        //Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
        progressDialog.show();
        new CountryService().getAPI1().astrologerlist(ApiLinks.productlist, "12").enqueue(new Callback<AstrologerResponse>() {
            @Override
            public void onResponse(Call<AstrologerResponse> call, Response<AstrologerResponse> response) {
                if (response.body()!=null){
                    progressDialog.dismiss();
                    if (response.body().getStatus().equals("true")){
                        List<AstrologerModel> servicelists=response.body().getData();
                        catagorylistAdaptor = new AstrologerListAdaptor(getContext(), servicelists);
                        chapterrecycler.setAdapter(catagorylistAdaptor);
                    }
                    else{
                        Toast.makeText(getContext(), "faild", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AstrologerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


//    public void loadslider() {
//        final List<Slide> slideList=new ArrayList<>();
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, "kdvn",
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("banner",response.toString());
//                        try {
//                            JSONObject outer=new JSONObject(response);
//                            JSONArray jsonArray=outer.getJSONArray("data");
//                            if (outer.getString("status").equals("true")) {
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject inner = jsonArray.getJSONObject(i);
//                                    slideList.add(new Slide(i+1,
//                                            ApiLinks.baseimgurl+inner.getString("image"),0));
//                                    // slideList.add(new Slide(i+1,"https://images.unsplash.com/photo-1512436991641-6745cdb1723f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80",0));
//                                }
//                                homeView.getSLider(slideList);
//                            }
//                            else{
//                                Toast.makeText(context,outer.getString("message"), Toast.LENGTH_LONG).show();
//                            }
//                        }
//                        catch (JSONException e) {
//                            Toast.makeText(context, e.toString()+"exception   ", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //You can handle error here if you want
//                        //   Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                //Adding parameters to request
//                //params.put("sub_category_id",subid);
//                //returning parameter
//                return params;
//            }
//        };
//
//        //Adding the string request to the queue
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//    }
}

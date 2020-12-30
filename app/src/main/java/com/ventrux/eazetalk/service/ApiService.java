package com.ventrux.eazetalk.service;



import com.ventrux.eazetalk.response.AstrologerResponse;
import com.ventrux.eazetalk.response.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface ApiService {

    @Headers({"Authorization: key=AAAA2qnSTuw:APA91bHdFESs_1a3nN7tAfMkCK8NYZEc5PvzJx81OEwgV_x1wyj-yoO-6suo6myOgKRxeFGm9KfzS934JFlYU4ab8dqQg7mtDE5pUPWOQcmTPTE-2CbN385DRp2TlE74FbyvjVOYCDKG",
            "Content-Type:application/json"})
    @FormUrlEncoded
    @POST
    Call<AstrologerResponse> astrologerlist(
            @Url String url,
            @Field("cat_id") String email_id);
    @FormUrlEncoded
    @POST
    Call<LoginResponse> login(
            @Url String url,
            @Field("id") String mobile,
            @Field("firebase_token") String password);
    @FormUrlEncoded
    @POST
    Call<LoginResponse> getfirebasetokencode(
            @Url String url,
            @Field("id") String id);

}



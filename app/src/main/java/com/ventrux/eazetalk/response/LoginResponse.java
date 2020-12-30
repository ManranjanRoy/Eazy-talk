package com.ventrux.eazetalk.response;

import com.google.gson.annotations.SerializedName;
import com.ventrux.eazetalk.model.Profile;


import java.util.List;

public class LoginResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String messages;
    @SerializedName("token_code")
    private String token_code;

    @SerializedName("data")
    private List<Profile> data;

    public List<Profile> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessages() {
        return messages;
    }

    public String getToken_code() {
        return token_code;
    }
}

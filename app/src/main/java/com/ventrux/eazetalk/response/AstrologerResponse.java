package com.ventrux.eazetalk.response;

import com.google.gson.annotations.SerializedName;
import com.ventrux.eazetalk.model.AstrologerModel;

import java.util.List;

public class AstrologerResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String messages;

    @SerializedName("data")
    private List<AstrologerModel> data;

    public List<AstrologerModel> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessages() {
        return messages;
    }
}

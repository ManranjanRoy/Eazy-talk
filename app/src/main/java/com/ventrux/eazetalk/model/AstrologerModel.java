package com.ventrux.eazetalk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AstrologerModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("exp")
    @Expose
    private String exp;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("specialization_name")
    @Expose
    private String specializationName;
    @SerializedName("chat_price")
    @Expose
    private String chatPrice;
    @SerializedName("voice_call_price")
    @Expose
    private String voiceCallPrice;
    @SerializedName("video_call_price")
    @Expose
    private String videoCallPrice;
    @SerializedName("about_me")
    @Expose
    private String aboutMe;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public String getChatPrice() {
        return chatPrice;
    }

    public void setChatPrice(String chatPrice) {
        this.chatPrice = chatPrice;
    }

    public String getVoiceCallPrice() {
        return voiceCallPrice;
    }

    public void setVoiceCallPrice(String voiceCallPrice) {
        this.voiceCallPrice = voiceCallPrice;
    }

    public String getVideoCallPrice() {
        return videoCallPrice;
    }

    public void setVideoCallPrice(String videoCallPrice) {
        this.videoCallPrice = videoCallPrice;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}
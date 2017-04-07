
package com.service.easyservice.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyApplianceResponse {

    @SerializedName("category")
    @Expose
    private Object category;
    @SerializedName("myappliance")
    @Expose
    private List<Myappliance> myappliance = null;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public List<Myappliance> getMyappliance() {
        return myappliance;
    }

    public void setMyappliance(List<Myappliance> myappliance) {
        this.myappliance = myappliance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

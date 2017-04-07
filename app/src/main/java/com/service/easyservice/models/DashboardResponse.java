
package com.service.easyservice.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardResponse {

    @SerializedName("category")
    @Expose
    private Object category;
    @SerializedName("totalmyappliance")
    @Expose
    private String totalmyappliance;
    @SerializedName("totalservicerequest")
    @Expose
    private String totalservicerequest;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public String getTotalmyappliance() {
        return totalmyappliance;
    }

    public void setTotalmyappliance(String totalmyappliance) {
        this.totalmyappliance = totalmyappliance;
    }

    public String getTotalservicerequest() {
        return totalservicerequest;
    }

    public void setTotalservicerequest(String totalservicerequest) {
        this.totalservicerequest = totalservicerequest;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

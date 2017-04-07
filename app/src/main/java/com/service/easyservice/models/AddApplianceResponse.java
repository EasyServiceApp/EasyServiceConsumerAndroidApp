
package com.service.easyservice.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddApplianceResponse {

    @SerializedName("new_user_appliance_id")
    @Expose
    private Integer newUserApplianceId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getNewUserApplianceId() {
        return newUserApplianceId;
    }

    public void setNewUserApplianceId(Integer newUserApplianceId) {
        this.newUserApplianceId = newUserApplianceId;
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

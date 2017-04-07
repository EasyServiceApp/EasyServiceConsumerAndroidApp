
package com.service.easyservice.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlotInfo {

    @SerializedName("datetimeslot")
    @Expose
    private List<Datetimeslot> datetimeslot = null;
    @SerializedName("request_date")
    @Expose
    private String requestDate;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Datetimeslot> getDatetimeslot() {
        return datetimeslot;
    }

    public void setDatetimeslot(List<Datetimeslot> datetimeslot) {
        this.datetimeslot = datetimeslot;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
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

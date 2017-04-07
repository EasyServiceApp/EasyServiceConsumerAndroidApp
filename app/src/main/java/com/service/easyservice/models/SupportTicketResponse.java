
package com.service.easyservice.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportTicketResponse {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("ticket_id")
    @Expose
    private Integer ticketId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
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

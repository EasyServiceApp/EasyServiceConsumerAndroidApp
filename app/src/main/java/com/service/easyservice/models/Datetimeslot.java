
package com.service.easyservice.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datetimeslot {

    @SerializedName("timeslot")
    @Expose
    private String timeslot;
    @SerializedName("availableslots")
    @Expose
    private Integer availableslots;

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public Integer getAvailableslots() {
        return availableslots;
    }

    public void setAvailableslots(Integer availableslots) {
        this.availableslots = availableslots;
    }

}


package com.service.easyservice.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPVerifyResponse {

    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rich_editing")
    @Expose
    private String richEditing;
    @SerializedName("comment_shortcuts")
    @Expose
    private String commentShortcuts;
    @SerializedName("admin_color")
    @Expose
    private String adminColor;
    @SerializedName("use_ssl")
    @Expose
    private String useSsl;
    @SerializedName("show_admin_bar_front")
    @Expose
    private String showAdminBarFront;
    @SerializedName("wp_capabilities")
    @Expose
    private String wpCapabilities;
    @SerializedName("wp_user_level")
    @Expose
    private String wpUserLevel;
    @SerializedName("dismissed_wp_pointers")
    @Expose
    private String dismissedWpPointers;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address1_tag")
    @Expose
    private String address1Tag;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("address2_tag")
    @Expose
    private String address2Tag;
    @SerializedName("address3")
    @Expose
    private String address3;
    @SerializedName("address3_tag")
    @Expose
    private String address3Tag;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pobox")
    @Expose
    private String pobox;
    @SerializedName("device_id")
    @Expose
    private Object deviceId;
    @SerializedName("session_tokens")
    @Expose
    private String sessionTokens;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRichEditing() {
        return richEditing;
    }

    public void setRichEditing(String richEditing) {
        this.richEditing = richEditing;
    }

    public String getCommentShortcuts() {
        return commentShortcuts;
    }

    public void setCommentShortcuts(String commentShortcuts) {
        this.commentShortcuts = commentShortcuts;
    }

    public String getAdminColor() {
        return adminColor;
    }

    public void setAdminColor(String adminColor) {
        this.adminColor = adminColor;
    }

    public String getUseSsl() {
        return useSsl;
    }

    public void setUseSsl(String useSsl) {
        this.useSsl = useSsl;
    }

    public String getShowAdminBarFront() {
        return showAdminBarFront;
    }

    public void setShowAdminBarFront(String showAdminBarFront) {
        this.showAdminBarFront = showAdminBarFront;
    }

    public String getWpCapabilities() {
        return wpCapabilities;
    }

    public void setWpCapabilities(String wpCapabilities) {
        this.wpCapabilities = wpCapabilities;
    }

    public String getWpUserLevel() {
        return wpUserLevel;
    }

    public void setWpUserLevel(String wpUserLevel) {
        this.wpUserLevel = wpUserLevel;
    }

    public String getDismissedWpPointers() {
        return dismissedWpPointers;
    }

    public void setDismissedWpPointers(String dismissedWpPointers) {
        this.dismissedWpPointers = dismissedWpPointers;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress1Tag() {
        return address1Tag;
    }

    public void setAddress1Tag(String address1Tag) {
        this.address1Tag = address1Tag;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress2Tag() {
        return address2Tag;
    }

    public void setAddress2Tag(String address2Tag) {
        this.address2Tag = address2Tag;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress3Tag() {
        return address3Tag;
    }

    public void setAddress3Tag(String address3Tag) {
        this.address3Tag = address3Tag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPobox() {
        return pobox;
    }

    public void setPobox(String pobox) {
        this.pobox = pobox;
    }

    public Object getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Object deviceId) {
        this.deviceId = deviceId;
    }

    public String getSessionTokens() {
        return sessionTokens;
    }

    public void setSessionTokens(String sessionTokens) {
        this.sessionTokens = sessionTokens;
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

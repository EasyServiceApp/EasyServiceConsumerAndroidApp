
package com.service.easyservice.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("subcategory_display")
    @Expose
    private Boolean subcategoryDisplay;
    @SerializedName("subcatgory")
    @Expose
    private List<Subcatgory> subcatgory = null;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getSubcategoryDisplay() {
        return subcategoryDisplay;
    }

    public void setSubcategoryDisplay(Boolean subcategoryDisplay) {
        this.subcategoryDisplay = subcategoryDisplay;
    }

    public List<Subcatgory> getSubcatgory() {
        return subcatgory;
    }

    public void setSubcatgory(List<Subcatgory> subcatgory) {
        this.subcatgory = subcatgory;
    }

}

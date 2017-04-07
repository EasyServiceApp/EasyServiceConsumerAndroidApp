
package com.service.easyservice.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Myappliance {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("serial_no")
    @Expose
    private String serialNo;
    @SerializedName("subcategory_id")
    @Expose
    private String subcategoryId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("model_id")
    @Expose
    private String modelId;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("warranty")
    @Expose
    private String warranty;
    @SerializedName("invoice1")
    @Expose
    private String invoice1;
    @SerializedName("invoice2")
    @Expose
    private String invoice2;
    @SerializedName("invoice3")
    @Expose
    private String invoice3;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("category_image_dir")
    @Expose
    private String categoryImageDir;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("brand")
    @Expose
    private String brand;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getInvoice1() {
        return invoice1;
    }

    public void setInvoice1(String invoice1) {
        this.invoice1 = invoice1;
    }

    public String getInvoice2() {
        return invoice2;
    }

    public void setInvoice2(String invoice2) {
        this.invoice2 = invoice2;
    }

    public String getInvoice3() {
        return invoice3;
    }

    public void setInvoice3(String invoice3) {
        this.invoice3 = invoice3;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryImageDir() {
        return categoryImageDir;
    }

    public void setCategoryImageDir(String categoryImageDir) {
        this.categoryImageDir = categoryImageDir;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}

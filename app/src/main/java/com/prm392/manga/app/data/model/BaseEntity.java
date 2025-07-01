package com.prm392.manga.app.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class BaseEntity {
    @SerializedName("Id")
    private int id;

    @SerializedName("CreatedAt")
    private String createdAt;

    @SerializedName("UpdatedAt")
    private String updatedAt;

    @SerializedName("IsDeleted")
    private boolean isDeleted;

    public BaseEntity() {}

    public BaseEntity(int id, String createdAt, String updatedAt, boolean isDeleted) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

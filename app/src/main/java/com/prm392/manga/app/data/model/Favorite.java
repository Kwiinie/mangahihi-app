package com.prm392.manga.app.data.model;

import com.google.gson.annotations.SerializedName;

public class Favorite extends BaseEntity {
    @SerializedName("UserId")
    private int userId;

    @SerializedName("ComicId")
    private int comicId;

    @SerializedName("ComicName")
    private String comicName;

    @SerializedName("CoverUrl")
    private String coverUrl;

    public Favorite() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}

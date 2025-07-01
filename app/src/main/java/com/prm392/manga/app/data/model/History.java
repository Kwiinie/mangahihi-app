package com.prm392.manga.app.data.model;

import com.google.gson.annotations.SerializedName;

public class History extends BaseEntity {
    @SerializedName("UserId")
    private int userId;

    @SerializedName("ComicId")
    private int comicId;

    @SerializedName("ChapterId")
    private int chapterId;

    public History() {}

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

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
}

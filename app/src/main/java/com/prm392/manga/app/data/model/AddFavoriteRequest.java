package com.prm392.manga.app.data.model;


import com.google.gson.annotations.SerializedName;

public class AddFavoriteRequest {
    @SerializedName("ComicId")
    private int comicId;

    public AddFavoriteRequest() {}

    public AddFavoriteRequest(int comicId) {
        this.comicId = comicId;
    }

    public int getComicId() { return comicId; }
    public void setComicId(int comicId) { this.comicId = comicId; }
}
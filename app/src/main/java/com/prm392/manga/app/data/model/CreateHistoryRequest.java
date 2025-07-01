package com.prm392.manga.app.data.model;

import com.google.gson.annotations.SerializedName;

public class CreateHistoryRequest {
    @SerializedName("ComicId")
    private int comicId;

    @SerializedName("ChapterId")
    private int chapterId;

    public CreateHistoryRequest() {}

    public CreateHistoryRequest(int comicId, int chapterId) {
        this.comicId = comicId;
        this.chapterId = chapterId;
    }

    public int getComicId() { return comicId; }
    public void setComicId(int comicId) { this.comicId = comicId; }

    public int getChapterId() { return chapterId; }
    public void setChapterId(int chapterId) { this.chapterId = chapterId; }
}

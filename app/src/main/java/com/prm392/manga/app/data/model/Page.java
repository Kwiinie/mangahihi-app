package com.prm392.manga.app.data.model;

import com.google.gson.annotations.SerializedName;

public class Page extends BaseEntity {
    @SerializedName("ChapterId")
    private int chapterId;

    @SerializedName("Chapter")
    private Chapter chapter;

    @SerializedName("PageNumber")
    private int pageNumber;

    @SerializedName("ImageUrl")
    private String imageUrl;

    public Page() {}

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

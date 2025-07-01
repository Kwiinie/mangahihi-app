package com.prm392.manga.app.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Chapter extends BaseEntity {
    @SerializedName("ComicId")
    private int comicId;

    @SerializedName("Comic")
    private Comic comic;

    @SerializedName("Number")
    private int number;

    @SerializedName("Slug")
    private String slug;

    @SerializedName("Title")
    private String title;

    @SerializedName("Pages")
    private List<Page> pages;

    public Chapter() {}

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}

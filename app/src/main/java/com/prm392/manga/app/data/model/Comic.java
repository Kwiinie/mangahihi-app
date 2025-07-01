package com.prm392.manga.app.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Comic extends BaseEntity implements Serializable {
    @SerializedName("Name")
    private String name;

    @SerializedName("Description")
    private String description;

    @SerializedName("ComicStatus")
    private String comicStatus;

    @SerializedName("Author")
    private String author;

    @SerializedName("IsPublic")
    private boolean isPublic;

    @SerializedName("Slug")
    private String slug;

    @SerializedName("View")
    private int view;

    @SerializedName("LatestChapter")
    private String latestChapter;

    @SerializedName("Type")
    private String type;

    @SerializedName("CoverUrl")
    private String coverUrl;

    @SerializedName("GenreNames")
    private List<String> genreNames;

    @SerializedName("Genres")
    private List<Genre> genres;

    @SerializedName("Chapters")
    private List<Chapter> chapters;

    @SerializedName("Comments")
    private List<Comment> comments;

    @SerializedName("Favorites")
    private List<Favorite> favorites;

    @SerializedName("Histories")
    private List<History> histories;

    public Comic() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComicStatus() {
        return comicStatus;
    }

    public void setComicStatus(String comicStatus) {
        this.comicStatus = comicStatus;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<String> getGenreNames() {
        return genreNames;
    }

    public void setGenreNames(List<String> genreNames) {
        this.genreNames = genreNames;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public String getLatestChapter() {
        return latestChapter;
    }

    public void setLatestChapter(String latestChapter) {
        this.latestChapter = latestChapter;
    }
}
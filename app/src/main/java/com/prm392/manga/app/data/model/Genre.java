package com.prm392.manga.app.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Genre extends BaseEntity {
    @SerializedName("Name")
    private String name;

    @SerializedName("Comics")
    private List<Comic> comics;

    public Genre() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }
}

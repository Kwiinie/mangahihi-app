package com.prm392.manga.app.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ODataResponse<T> {
    @SerializedName("@odata.context")
    private String context;

    @SerializedName("value")
    private List<T> value;

    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }

    public List<T> getValue() { return value; }
    public void setValue(List<T> value) { this.value = value; }
}

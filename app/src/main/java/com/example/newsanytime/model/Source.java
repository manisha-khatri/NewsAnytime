
package com.example.newsanytime.model;

import android.support.annotation.Nullable;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source implements Serializable
{

    @SerializedName("id")
    @Expose
    @Nullable
    private String id;

    @SerializedName("name")
    @Expose
    @Nullable
    private String name;

    private final static long serialVersionUID = 6253492656593522955L;

    public Source(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Object getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

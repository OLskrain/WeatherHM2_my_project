package com.example.olskr.weatherhm2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//"clouds":{"all":75}

//облачность
public class Clouds {
    @SerializedName("all")
    @Expose
    private int all;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}

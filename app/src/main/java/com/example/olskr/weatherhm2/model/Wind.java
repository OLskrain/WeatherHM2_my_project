package com.example.olskr.weatherhm2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//"wind":{"speed":1,"deg":140}

//ветер
public class Wind {
    @SerializedName("speed")
    @Expose
    private float speed;
    @SerializedName("deg")
    @Expose
    //градус поворода(направление)
    private float deg;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDeg() {
        return deg;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }
}

package com.example.idata_android.Model;

import java.sql.Timestamp;

public class Temperature {
    private int temperature_id;
    private float temperature;
    private int elder_ID;
    private Timestamp timestamp;

    public Temperature(int temperature_id, float temperature, int elder_ID, Timestamp timestamp) {
        this.temperature_id = temperature_id;
        this.temperature = temperature;
        this.elder_ID = elder_ID;
        this.timestamp = timestamp;
    }

    public int getTemperature_id() {
        return temperature_id;
    }

    public void setTemperature_id(int temperature_id) {
        this.temperature_id = temperature_id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getElder_ID() {
        return elder_ID;
    }

    public void setElder_ID(int elder_ID) {
        this.elder_ID = elder_ID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

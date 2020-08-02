package com.example.idata_android.Model;

import java.sql.Timestamp;

public class Temperature {
    public int getTemperatureID() {
        return temperatureID;
    }

    public void setTemperatureID(int temperatureID) {
        this.temperatureID = temperatureID;
    }

    private int temperatureID;
    private float temperature;
    private int elder_ID;
    private Timestamp timestamp;

    public Temperature(int temperatureID, float temperature, int elder_ID, Timestamp timestamp) {
        this.temperatureID = temperatureID;
        this.temperature = temperature;
        this.elder_ID = elder_ID;
        this.timestamp = timestamp;
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

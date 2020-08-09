package com.example.idata_android.Model;

import java.sql.Timestamp;

public class Temperature {



    private int temperature_id_local;

    private int temperature_id;
    private double temperature;
    private int elder_id;
    private Timestamp timestamp;
   // private int status;


    public Temperature(int temperature_id, double temperature, int elder_ID, Timestamp timestamp) {
        this.temperature_id = temperature_id;
        this.temperature = temperature;
        this.elder_id = elder_id;
        this.timestamp = timestamp;
    }

    public Temperature() {
    }

    public int getTemperature_id_local() {
        return temperature_id_local;
    }

    public void setTemperature_id_local(int temperature_id_local) {
        this.temperature_id_local = temperature_id_local;
    }

    public int getTemperature_id() {
        return temperature_id;
    }

    public void setTemperature_id(int temperature_id) {
        this.temperature_id = temperature_id;
    }

    public int getElder_id() {
        return elder_id;
    }

    public void setElder_id(int elder_id) {
        this.elder_id = elder_id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "temperature_id=" + temperature_id +
                ", temperature=" + temperature +
                ", elder_id=" + elder_id +
                ", timestamp=" + timestamp +
                '}';
    }
}

package hk.com.caretech.clive.idata_android.Model;

import java.sql.Timestamp;

public class Temperature {


    private double temperature;
    private int elder_id;
    private String device_id;
    private Timestamp timestamp;

    public Temperature() {
    }

    public Temperature(double temperature, int elder_id, String device_id, Timestamp timestamp) {
        this.temperature = temperature;
        this.elder_id = elder_id;
        this.device_id = device_id;
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getElder_id() {
        return elder_id;
    }

    public void setElder_id(int elder_id) {
        this.elder_id = elder_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
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
                "temperature=" + temperature +
                ", elder_id=" + elder_id +
                ", device_id='" + device_id + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

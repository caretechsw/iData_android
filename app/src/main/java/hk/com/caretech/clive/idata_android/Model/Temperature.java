package hk.com.caretech.clive.idata_android.Model;

import java.sql.Timestamp;

public class Temperature {

    private int temperature_id;
    private double temperature;
    private int elder_id;
    private Timestamp timestamp;

    public Temperature(int temperature_id, double temperature, int elder_id, Timestamp timestamp) {
        this.temperature_id = temperature_id;
        this.temperature = temperature;
        this.elder_id = elder_id;
        this.timestamp = timestamp;
    }

    public Temperature() {
    }

    public int getTemperature_id() {
        return temperature_id;
    }

    public void setTemperature_id(int temperature_id) {
        this.temperature_id = temperature_id;
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

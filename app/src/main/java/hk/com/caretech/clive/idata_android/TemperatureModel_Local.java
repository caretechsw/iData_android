package hk.com.caretech.clive.idata_android;

import java.sql.Timestamp;

public class TemperatureModel_Local {

    private double temperature;
    private String elder_id;
    private String device_id;
    private Timestamp timestamp;
    private int status;

    public TemperatureModel_Local(double temperature, String elder_id, String device_id, Timestamp timestamp, int status) {
        this.elder_id = elder_id;
        this.temperature = temperature;
        this.device_id = device_id;
        this.timestamp = timestamp;
        this.status = status;
    }


    public String getElder_id() {
        return elder_id;
    }

    public void setElder_id(String elder_id) {
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


    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TemperatureModel_Local{" +
                "device_id='" + device_id + '\'' +
                ", temperature=" + temperature +
                ", elder_id=" + elder_id +
                ", timestamp=" + timestamp +
                ", status=" + status +
                '}';
    }
}

package hk.com.caretech.clive.idata_android;

import java.sql.Timestamp;

public class TemperatureModel_Local {

    private int elder_id;
    private float temperature;
    private String device_id;
    private long timestamp;
    private int status;

    public TemperatureModel_Local(int elder_id, float temperature, String device_id, long timestamp, int status) {
        this.elder_id = elder_id;
        this.temperature = temperature;
        this.device_id = device_id;
        this.timestamp = timestamp;
        this.status = status;
    }


    public int getElder_id() {
        return elder_id;
    }

    public void setElder_id(int elder_id) {
        this.elder_id = elder_id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
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

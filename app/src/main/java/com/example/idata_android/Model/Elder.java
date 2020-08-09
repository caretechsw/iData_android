package com.example.idata_android.Model;

public class Elder {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBed_no() {
        return bed_no;
    }

    public void setBed_no(int bed_no) {
        this.bed_no = bed_no;
    }

    private int id;
    private String name;
    private int bed_no;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public Elder(int id, String name, int bed_no) {
        this.id = id;
        this.name = name;
        this.bed_no = bed_no;
    }

    @Override
    public String toString() {
        return "Elder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bed_no=" + bed_no +
                ", status=" + status +
                '}';
    }
}

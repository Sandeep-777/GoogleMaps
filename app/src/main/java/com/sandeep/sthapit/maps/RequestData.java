package com.sandeep.sthapit.maps;

/**
 * Created by sandeep on 8/28/16.
 */
public class RequestData {

    String v_no;
    String type;
    int capacity;
    String status;
    String time;
    String location;

    public RequestData(String v_no, String type, int capacity, String status, String time, String location) {
        this.v_no = v_no;
        this.type = type;
        this.capacity = capacity;
        this.status = status;
        this.time = time;
        this.location = location;

    }

    public String getVno() {
        return v_no;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public void setV_no(String v_no){
        this.v_no = v_no;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setTime(String time){
        this.time = time;
    }

}


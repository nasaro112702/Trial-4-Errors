package com.example.pmsqrcqueue;

public class DentalService {
    static String service = "";

    public DentalService(){

    }

    public DentalService(String service){
        this.service = service;
    }

    public String getService(){
        return service;
    }

    public void clearService(){
        service = "";
    }
}

package com.example.pmsqrcqueue;

public class Host {
    static String address;
    public Host(String address){
        this.address = address;
    }
    public Host(){
    }

    public String getAddress(){
        return address;
    }
}

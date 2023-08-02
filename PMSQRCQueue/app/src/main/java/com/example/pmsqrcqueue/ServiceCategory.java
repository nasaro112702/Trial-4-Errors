package com.example.pmsqrcqueue;

public class ServiceCategory {

    static String category = "";

    public ServiceCategory(){

    }

    public ServiceCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public void clearCategory(){
        category = "";
    }
}

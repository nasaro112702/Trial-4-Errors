package com.example.recyclerviewlinear;

public class recycler_items {

    private String version_name;
    private String version;

    public recycler_items(String version_name, String version){
        this.version_name = version_name;
        this.version = version;
    }

    public String getVersion_name(){
        return version_name;
    }

    public String getVersion_(){
        return version;
    }
}

package com.example.recyclerview1;

public class RecyclerItems {

    private String version_name;
    private String version;

    public RecyclerItems(String version_name, String version){
        this.version_name = version_name;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String getVersion_name() {
        return version_name;
    }
}

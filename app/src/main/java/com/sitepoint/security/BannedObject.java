package com.sitepoint.security;

public class BannedObject {

    private String name;
    private Integer picture;
    private String reason;

    public BannedObject(String name, Integer picture, String reason) {
        this.name = name;
        this.picture = picture;
        this.reason = reason;
    }

    public String getName(){
        return name;
    }

    public Integer getPicture(){
        return picture;
    }

    public String getReason(){
        return reason;
    }
}
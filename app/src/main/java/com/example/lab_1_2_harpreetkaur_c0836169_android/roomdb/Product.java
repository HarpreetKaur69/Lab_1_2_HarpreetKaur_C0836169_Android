package com.example.lab_1_2_harpreetkaur_c0836169_android.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String desc;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "price")
    private int price;

    public Product() {
    }

    public Product(String name, String desc, String price, String lat, String longitude) {
        this.name = name;
        this.desc = desc;
        this.price = Integer.valueOf(price);
        this.latitude = lat;
        this.longitude = longitude;
    }

    public Product(int id, String name, String desc, String price, String lat, String longitude) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = Integer.valueOf(price);
        this.latitude = lat;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String task) {
        this.name = task;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

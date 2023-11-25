package com.example.libapp.model;

import java.io.Serializable;

public class SchoolSupply implements Serializable {
    private int id;
    private String name;
    private int type_id;
    private String ages;
    private String image;
    private Double price;

    public SchoolSupply(int id, String name, int type_id, String ages, String image, Double price) {
        this.id = id;
        this.name = name;
        this.type_id = type_id;
        this.ages = ages;
        this.image = image;
        this.price = price;
    }

    public SchoolSupply( String name, int type_id, String ages, String image, Double price) {
        this.name = name;
        this.type_id = type_id;
        this.ages = ages;
        this.image = image;
        this.price = price;
    }

    public SchoolSupply() {
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

    public void setName(String name) {
        this.name = name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getAges() {
        return ages;
    }

    public void setAges(String ages) {
        this.ages = ages;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return  id +
                "-" + name +
                "-" + type_id +
                "-" + ages +
                "-" + image +
                "-" + price ;
    }
}

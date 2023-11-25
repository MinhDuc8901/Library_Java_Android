package com.example.libapp.model;

import android.text.Editable;

import java.io.Serializable;

public class TypeSchoolSupply implements Serializable {
    private int id;
    private String name;
    private String desc;

    public TypeSchoolSupply(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public TypeSchoolSupply(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public TypeSchoolSupply() {
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return   id +
                "-" + name +
                "-" + desc ;
    }
}

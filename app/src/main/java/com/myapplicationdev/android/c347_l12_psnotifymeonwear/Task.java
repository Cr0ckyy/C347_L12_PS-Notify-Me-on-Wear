package com.myapplicationdev.android.c347_l12_psnotifymeonwear;

import java.io.Serializable;

class Task implements Serializable {

    private int id;
    private String name;
    private String desc;

    public Task(int id, String name, String desc){
        this.id = id;
        this.desc = desc;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId(){
        return id;
    }

    public String getDesc(){
        return desc;
    }

    public  void setId(int id){
        this.id = id;
    }

    public void  setDesc(String desc){
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + "\n" + desc + "\n" + name;
    }
}
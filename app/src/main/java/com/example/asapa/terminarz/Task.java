package com.example.asapa.terminarz;

public class Task {

    private long id;
    private String name, desc, prio, due_date;

    public void setId(long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setPrio(String prio){
        this.prio = prio;
    }

    public void setDue_date(String due_date){
        this.due_date = due_date;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getDesc(){
        return this.desc;
    }

    public String getPrio(){
        return this.prio;
    }

    public String getDue_date(){
        return this.due_date;
    }
}

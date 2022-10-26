package com.example.mcc;

public class Participant {
    String group, name, handle;

    public Participant() {
    }

    public Participant(String group, String name, String handle) {
        this.group = group;
        this.name = name;
        this.handle = handle;
    }

    public Participant(String group, String name) {
        this.group = group;
        this.name = name;
        this.handle = "EVENT";
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
}
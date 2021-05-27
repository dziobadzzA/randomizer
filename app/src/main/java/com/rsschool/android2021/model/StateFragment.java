package com.rsschool.android2021.model;

public class StateFragment {

    private boolean v;
    private String name;

    public StateFragment(String name) {
        this.name = name;
        this.v = false;
    }

    public void updateStateFragment() {
        this.v = !this.v;
    }

    public boolean checkStateFragment() {
        return this.v;
    }

    public String getName() {
        return this.name;
    }

}

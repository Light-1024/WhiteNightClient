package com.code.sjaiaa.event;

public enum EventState {
    PRE("PRE"), POST("POST");

    private String stateName;

    EventState(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }
}

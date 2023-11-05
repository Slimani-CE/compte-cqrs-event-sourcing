package com.slimanice.comptecqrses.commonapi.events;

import lombok.Getter;

import java.util.Date;

@Getter
public abstract class BaseEvent<T> {
    private T id;
    private Date date;

    public BaseEvent(T id, Date date) {
        this.id = id;
        this.date = date;
    }
}

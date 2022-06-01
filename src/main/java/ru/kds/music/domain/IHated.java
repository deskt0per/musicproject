package ru.kds.music.domain;

public interface IHated {

    Long getId();

    boolean isHated();

    void setHated(boolean isHated);

    String getObjectType();
}

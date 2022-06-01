package ru.kds.music.domain;

public interface ILiked {

    Long getId();

    boolean isLiked();

    void setLiked(boolean isLiked);

    String getObjectType();
}

package ru.kds.music.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtistRow implements ILiked, Serializable {

    private Long id;

    private String name;

    private String description;

    private boolean liked;

    @Override
    public String getObjectType() {
        return Artist.class.getName();
    }
}

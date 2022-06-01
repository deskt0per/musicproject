package ru.kds.music.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackRow implements ILiked, IHated, Serializable {

    private Long id;

    private String name;

    private String artistName;

    private String url;

    private boolean liked;

    private boolean hated;

    @Override
    public String getObjectType() {
        return Track.class.getName();
    }
}

package ru.kds.music.ui.components;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import ru.kds.music.domain.Album;
import ru.kds.music.ui.AlbumView;

public class AlbumGrid extends Grid<Album> {

    public AlbumGrid() {
        super(Album.class, false);
        setSelectionMode(SelectionMode.NONE);

        addColumn(new ComponentRenderer<>((album) -> new RouterLink(album.getName(), AlbumView.class,
                new RouteParameters(new RouteParam("artistId", album.getArtist().getId() + ""),
                        new RouteParam("id", album.getId() + "")))));
    }
}

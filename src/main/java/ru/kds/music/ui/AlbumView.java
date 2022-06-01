package ru.kds.music.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import ru.kds.music.domain.Album;
import ru.kds.music.domain.AlbumRepository;
import ru.kds.music.domain.TrackRepository;
import ru.kds.music.domain.TrackRow;
import ru.kds.music.service.HateService;
import ru.kds.music.service.LikeService;
import ru.kds.music.ui.components.TrackGrid;

@Route(value = "artists/:artistId/albums/:id", layout = DefaultLayout.class)
public class AlbumView extends VerticalLayout implements BeforeEnterObserver {


    private final AlbumRepository albumRepository;

    private final TrackRepository trackRepository;

    private final LikeService likeService;

    private final HateService hateService;

    public AlbumView(AlbumRepository albumRepository, TrackRepository trackRepository, LikeService likeService,
            HateService hateService) {
        super();
        setSizeFull();

        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
        this.likeService = likeService;
        this.hateService = hateService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEvent) {
        Long id = beforeEvent.getRouteParameters().getLong("id").orElseThrow(() -> new IllegalArgumentException());
        Album album = albumRepository.findByIdWithArtist(id).orElseThrow(() -> new IllegalArgumentException());

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        H2 h2 = new H2(album.getArtist().getName() + " | " + album.getName());
        RouterLink backLink = new RouterLink("back", ArtistView.class,
                new RouteParameters(new RouteParam("id", album.getArtist().getId() + "")));
        backLink.getStyle().set("margin-left", "auto");
        header.add(h2, backLink);

        Grid<TrackRow> trackGrid = new TrackGrid(likeService, hateService);
        trackGrid.setItems(trackRepository.findByAlbum(album));

        setFlexGrow(0, header);
        setFlexGrow(1, trackGrid);
        expand(trackGrid);

        add(header, trackGrid);
    }
}

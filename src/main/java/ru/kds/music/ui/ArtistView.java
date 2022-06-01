package ru.kds.music.ui;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import ru.kds.music.domain.Album;
import ru.kds.music.domain.AlbumRepository;
import ru.kds.music.domain.Artist;
import ru.kds.music.domain.ArtistRepository;
import ru.kds.music.domain.TrackRepository;
import ru.kds.music.domain.TrackRow;
import ru.kds.music.service.HateService;
import ru.kds.music.service.LikeService;
import ru.kds.music.ui.components.AlbumGrid;
import ru.kds.music.ui.components.TrackGrid;

@Route(value = "artists/:id", layout = DefaultLayout.class)
public class ArtistView extends VerticalLayout implements BeforeEnterObserver {

    private final ArtistRepository artistRepository;

    private final AlbumRepository albumRepository;

    private final TrackRepository trackRepository;

    private final LikeService likeService;

    private final HateService hateService;

    public ArtistView(ArtistRepository artistRepository, AlbumRepository albumRepository,
            TrackRepository trackRepository, LikeService likeService, HateService hateService) {
        super();
        setSizeFull();

        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
        this.likeService = likeService;
        this.hateService = hateService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEvent) {
        Long id = beforeEvent.getRouteParameters().getLong("id").orElseThrow(() -> new IllegalArgumentException());
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        H2 h2 = new H2(artist.getName());
        RouterLink backLink = new RouterLink("back", ArtistsView.class);
        backLink.getStyle().set("margin-left", "auto");
        header.add(h2, backLink);

        Tabs tabs = new Tabs();
        Tab albumsTab = new Tab("Albums");
        Tab tracksTab = new Tab("Tracks");
        tabs.add(albumsTab, tracksTab);

        VerticalLayout tabContent = new VerticalLayout();
        tabContent.setSizeFull();

        ComponentEventListener<Tabs.SelectedChangeEvent> tabsEventListener = event -> {
            tabContent.removeAll();
            if (albumsTab == event.getSelectedTab()) {
                tabContent.add(createAlbumsTab(artist));
            } else {
                tabContent.add(createTracksTab(artist));
            }
        };
        tabs.addSelectedChangeListener(tabsEventListener);
        tabsEventListener.onComponentEvent(new Tabs.SelectedChangeEvent(tabs, albumsTab, false));

        setFlexGrow(0, header);
        setFlexGrow(1, tabs);
        setFlexGrow(2, tabContent);
        expand(tabContent);

        add(header, tabs, tabContent);
    }

    private Grid createAlbumsTab(Artist artist) {
        Grid<Album> albumGrid = new AlbumGrid();
        albumGrid.setItems(albumRepository.findByArtist(artist));

        return albumGrid;
    }

    private Grid createTracksTab(Artist artist) {
        Grid<TrackRow> trackGrid = new TrackGrid(likeService, hateService);
        trackGrid.setItems(trackRepository.findByArtist(artist));

        return trackGrid;
    }
}

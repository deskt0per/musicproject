package ru.kds.music.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.kds.music.domain.ArtistRow;
import ru.kds.music.service.ArtistService;
import ru.kds.music.service.LikeService;
import ru.kds.music.ui.components.ArtistsGrid;

@Route(value = "artists", layout = DefaultLayout.class)
public class ArtistsView extends VerticalLayout {

    public ArtistsView(ArtistService artistService, LikeService likeService) {
        super();
        setSizeFull();

        HorizontalLayout header = new HorizontalLayout();
        H2 h2 = new H2("Artists");
        header.add(h2);

        Grid<ArtistRow> artistGrid = new ArtistsGrid(likeService);
        artistGrid.setItems(artistService.findArtistsByNameContains(""));

        setFlexGrow(0, header);
        setFlexGrow(1, artistGrid);
        expand(artistGrid);

        add(header, artistGrid);
    }
}

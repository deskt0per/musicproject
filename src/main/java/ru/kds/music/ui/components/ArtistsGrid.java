package ru.kds.music.ui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import ru.kds.music.domain.ArtistRow;
import ru.kds.music.service.LikeService;
import ru.kds.music.ui.ArtistView;

public class ArtistsGrid extends Grid<ArtistRow> {

    public ArtistsGrid(LikeService likeService) {
        super(ArtistRow.class, false);
        setSizeFull();
        setSelectionMode(SelectionMode.NONE);

        addColumn(new ComponentRenderer<>((artistRow) -> new RouterLink(artistRow.getName(), ArtistView.class,
                new RouteParameters(new RouteParam("id", artistRow.getId() + "")))));
        Grid.Column likeColumn = addColumn(new LikeRenderer(likeService));

        likeColumn.setWidth("60px").setFlexGrow(0);

        addItemClickListener(clickEvent -> {
            ArtistRow item = clickEvent.getItem();
            if (clickEvent.getColumn() == likeColumn) {
                return;
            }
            UI.getCurrent().navigate("/artists/" + item.getId());
        });
    }
}

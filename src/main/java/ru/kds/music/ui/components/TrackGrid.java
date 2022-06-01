package ru.kds.music.ui.components;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.LitRenderer;
import ru.kds.music.domain.TrackRow;
import ru.kds.music.service.HateService;
import ru.kds.music.service.LikeService;

public class TrackGrid extends Grid<TrackRow> {

    public TrackGrid(LikeService likeService, HateService hateService) {
        this(likeService, hateService, () -> {
        });
    }

    public TrackGrid(LikeService likeService, HateService hateService, Runnable onHate) {
        super(TrackRow.class, false);
        setSizeFull();
        setSelectionMode(SelectionMode.NONE);

        addColumn(LitRenderer.<TrackRow>of(
                        "${item.name} " +
                                "<small style=\"color: var(--lumo-secondary-text-color); font-size: var(--lumo-font-size-s);\" >${item.artistName}</small>")
                .withProperty("name", TrackRow::getName)
                .withProperty("artistName", TrackRow::getArtistName));
        addColumn(LitRenderer.<TrackRow>of("<audio controls=\"true\" preload=\"none\" src=\"${item.url}\"/>")
                .withProperty("url", TrackRow::getUrl)).setWidth("320px").setFlexGrow(0);
        addColumn(new LikeRenderer(likeService)).setWidth("60px").setFlexGrow(0);
        addColumn(new HateRenderer(hateService, onHate)).setWidth("60px").setFlexGrow(0);
    }
}

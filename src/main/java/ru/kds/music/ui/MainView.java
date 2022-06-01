package ru.kds.music.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import java.util.List;
import ru.kds.music.domain.TrackRow;
import ru.kds.music.service.HateService;
import ru.kds.music.service.LikeService;
import ru.kds.music.service.TrackService;
import ru.kds.music.ui.components.TrackGrid;

@Route(layout = DefaultLayout.class)
public class MainView extends VerticalLayout {

    public MainView(TrackService trackService, LikeService likeService, HateService hateService) {
        super();
        setSizeFull();

        HorizontalLayout header = new HorizontalLayout();
        H1 h1 = new H1("Recommended");
        header.add(h1);
        List<TrackRow> tracks = trackService.findRecommendedTracks();
        ListDataProvider<TrackRow> dataProvider;
        Runnable onHate = () -> {};
        if (tracks.isEmpty()) {
            tracks = trackService.findLastTracks();
             dataProvider= new ListDataProvider(tracks);
            h1.setText("Like artists or tracks for better playlist");
        } else {
            dataProvider= new ListDataProvider(tracks);
            onHate = () -> {
                dataProvider.getItems().clear();
                dataProvider.getItems().addAll(trackService.findRecommendedTracks());
                dataProvider.refreshAll();
            };

        }


        TrackGrid trackGrid = new TrackGrid(likeService, hateService, onHate);
        trackGrid.setItems(dataProvider);

        setFlexGrow(0, header);
        setFlexGrow(1, trackGrid);
        expand(trackGrid);

        add(header, trackGrid);
    }
}

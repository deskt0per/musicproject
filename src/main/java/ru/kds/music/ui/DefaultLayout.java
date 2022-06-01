package ru.kds.music.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import ru.kds.music.utils.SecurityUtils;

public class DefaultLayout extends AppLayout {

    private VerticalLayout content = new VerticalLayout();

    public DefaultLayout() {
        DrawerToggle toggle = new DrawerToggle();
        Label title = new Label("Music");
        title.addComponentAsFirst(new Icon(VaadinIcon.MUSIC));
        addToNavbar(toggle, title);

        Div rightGroup = new Div();
        rightGroup.getStyle().set("margin-left", "auto");
        rightGroup.getStyle().set("padding-right", "20px");

        Label userName = new Label(SecurityUtils.getPrincipal().getUsername());
        userName.getStyle().set("margin", "5px");
        Anchor logoutAnchor = new Anchor("/login?logout", "logout");
        logoutAnchor.getStyle().set("margin", "5px");
        rightGroup.add(userName, logoutAnchor);

        addToNavbar(toggle, title, rightGroup);
        Tabs tabs = createTabs();

        addToDrawer(tabs);

        setContent(content);
    }

    private Tabs createTabs() {
        Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        Tab mainTab = new Tab();
        mainTab.add(new RouterLink("Main", MainView.class));
        Tab artistsTab = new Tab();
        artistsTab.add(new RouterLink("Artists", ArtistsView.class));

        tabs.add(mainTab, artistsTab);

        return tabs;
    }

    protected void addContent(Component... components) {
        content.add(components);
    }
}

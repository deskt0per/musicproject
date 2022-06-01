package ru.kds.music.ui.components;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;

@Tag("form")
public class HtmlForm extends HtmlContainer {

    public void setAction(String action) {
        getElement().setProperty("action", action);
    }

    public void setMethod(String method) {
        getElement().setProperty("method", method);
    }
}

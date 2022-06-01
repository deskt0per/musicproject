package ru.kds.music.ui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import ru.kds.music.domain.IHated;
import ru.kds.music.service.HateService;

public class HateRenderer<T extends IHated> extends ComponentRenderer<Button, T> {

    public HateRenderer(HateService hateService, Runnable onClick) {
        super(Button::new, (button, hated) -> {
            if (hated.isHated()) {
                button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            }
            button.setId("hateButton" + hated.getId());
            button.setIcon(VaadinIcon.THUMBS_DOWN.create());
            button.addClickListener(e -> {
                if (hated.isHated()) {
                    button.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    hateService.deleteHatedObject(hated.getId(), hated.getObjectType());
                    hated.setHated(false);
                } else {
                    button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    hateService.hateObject(hated.getId(), hated.getObjectType());
                    hated.setHated(true);
                }
                onClick.run();
            });
        });
    }
}

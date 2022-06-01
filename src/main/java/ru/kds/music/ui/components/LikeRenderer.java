package ru.kds.music.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import ru.kds.music.domain.ILiked;
import ru.kds.music.service.LikeService;

public class LikeRenderer<T extends ILiked> extends ComponentRenderer<Button, T> {

    public LikeRenderer(LikeService likeService) {
        super(Button::new, (button, liked) -> {
            if (liked.isLiked()) {
                button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            }
            button.setId("likeButton" + liked.getId());
            button.setIcon(VaadinIcon.THUMBS_UP.create());
            button.addClickListener(e -> {
                if (liked.isLiked()) {
                    button.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    likeService.deleteLikedObject(liked.getId(), liked.getObjectType());
                    liked.setLiked(false);
                } else {
                    button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    likeService.likeObject(liked.getId(), liked.getObjectType());
                    liked.setLiked(true);
                }
            });
        });
    }
}

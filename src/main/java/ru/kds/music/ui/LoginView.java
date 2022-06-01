package ru.kds.music.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import java.util.Collections;
import ru.kds.music.ui.components.HtmlForm;
import ru.kds.music.ui.components.LoginForm;

@Route("login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm loginForm;

    public LoginView() {
        HtmlForm form = new HtmlForm();
        form.setAction("/login");
        form.setMethod("POST");
        loginForm = new LoginForm();
        // Center the RegistrationForm
        setHorizontalComponentAlignment(Alignment.CENTER, form);
        RouterLink registrationLink = new RouterLink("Sign up", RegistrationView.class);
        form.add(loginForm, registrationLink);
        form.setMaxWidth("460px");
        add(form);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // inform the user about an authentication error
        // (yes, the API for resolving query parameters is annoying...)
        if (!event.getLocation()
                .getQueryParameters()
                .getParameters()
                .getOrDefault("error", Collections.emptyList())
                .isEmpty()) {
            loginForm.setError(true);
        }
    }
}

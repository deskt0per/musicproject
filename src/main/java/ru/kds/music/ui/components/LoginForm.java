package ru.kds.music.ui.components;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import java.util.stream.Stream;

public class LoginForm extends FormLayout {

    private H3 title;

    private TextField username;

    private PasswordField password;

    private Button submitButton;

    private Span errorMessageField;

    public LoginForm() {
        title = new H3();
        title.add(new Icon(VaadinIcon.MUSIC));
        title.add(new Span("Music"));

        username = new TextField("Username");
        username.getElement().setAttribute("name", "username");

        password = new PasswordField("Password");
        password.getElement().setAttribute("name", "password");

        setRequiredIndicatorVisible(username, password);

        errorMessageField = new Span();
        errorMessageField.setText("Invalid username or password");
        errorMessageField.setVisible(false);

        submitButton = new Button("Log in");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(buttonClickEvent -> {
            UI.getCurrent().getPage().executeJs("document.querySelector('form').submit()");
        });

        add(title, errorMessageField, username, password, submitButton);

        // Max width of the Form
        setMaxWidth("460px");
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    public void setAction(String action) {
        getElement().setProperty("action", action);
    }

    public void setError(boolean error) {
        errorMessageField.setVisible(error);
    }
}

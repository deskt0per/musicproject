package ru.kds.music.ui.components;

import com.vaadin.flow.component.HasValueAndElement;
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

public class RegistrationForm extends FormLayout {

    private H3 title;

    private TextField username;

    private PasswordField password;

    private Span errorMessageField;

    private Button submitButton;


    public RegistrationForm() {
        title = new H3();
        title.add(new Icon(VaadinIcon.MUSIC));
        title.add(new Span("Signup form"));

        username = new TextField("Username");

        password = new PasswordField("Password");

        setRequiredIndicatorVisible(username, password);

        errorMessageField = new Span();

        submitButton = new Button("Sign up");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(title, username, password, errorMessageField, submitButton);

        // Max width of the Form
        setMaxWidth("460px");
    }

    public PasswordField getPasswordField() {
        return password;
    }

    public Span getErrorMessageField() {
        return errorMessageField;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }
}

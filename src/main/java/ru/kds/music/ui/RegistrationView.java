package ru.kds.music.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import ru.kds.music.service.RegistrationService;
import ru.kds.music.ui.components.RegistrationForm;
import ru.kds.music.ui.components.RegistrationFormBinder;

@Route("registration")
public class RegistrationView extends VerticalLayout {

    public RegistrationView(RegistrationService registrationService) {
        RegistrationForm registrationForm = new RegistrationForm();
        // Center the RegistrationForm
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

        add(registrationForm);

        RouterLink loginLink = new RouterLink("Sign in", LoginView.class);
        registrationForm.add(loginLink);

        RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm,
                registrationService);
        registrationFormBinder.addBindingAndValidation();
    }
}

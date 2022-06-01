package ru.kds.music.ui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import ru.kds.music.domain.User;
import ru.kds.music.service.RegistrationService;
import ru.kds.music.service.UserAlreadyExistException;

public class RegistrationFormBinder {

    private RegistrationForm registrationForm;

    private RegistrationService registrationService;

    public RegistrationFormBinder(RegistrationForm registrationForm, RegistrationService registrationService) {
        this.registrationForm = registrationForm;
        this.registrationService = registrationService;
    }

    /**
     * Method to add the data binding and validation logics to the registration form
     */
    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(registrationForm);

        // A custom validator for password fields
        binder.forField(registrationForm.getPasswordField()).withValidator(this::passwordValidator).bind("password");

        // Set the label where bean-level error messages go
        binder.setStatusLabel(registrationForm.getErrorMessageField());

        // And finally the submit button
        registrationForm.getSubmitButton().addClickListener(event -> {
            try {
                // Create empty bean to store the details into
                User user = new User();

                // Run validators and write the values to the bean
                binder.writeBean(user);

                // Typically, you would here call backend to store the bean
                registrationService.register(user.getUsername(), user.getPassword());
                // Show success message if everything went well
                UI.getCurrent().navigate("/login");
            } catch (UserAlreadyExistException exception) {
                registrationForm.getErrorMessageField().setText("User already exist");
            } catch (ValidationException exception) {
                // validation errors are already visible for each field,
                // and bean-level errors are shown in the status label.
                // We could show additional messages here if we want, do logging, etc.
            }
        });
    }

    /**
     * Method to validate that:
     * <p>
     * 1) Password is at least 8 characters long
     * <p>
     * 2) Values in both fields match each other
     */
    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
        /*
         * Just a simple length check. A real version should check for password
         * complexity as well!
         */

        if (pass1 == null || pass1.length() < 8) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        return ValidationResult.ok();
    }
}


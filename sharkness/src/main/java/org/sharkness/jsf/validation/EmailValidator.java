package org.sharkness.jsf.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class EmailValidator extends ShkValidator {

    private static final String EMAIL_REGEXP = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void validate(FacesContext context, UIComponent c, Object val) throws ValidatorException {
        String email = (String) val;
        Pattern mask = null;
        mask = Pattern.compile(EMAIL_REGEXP);
        Matcher matcher = mask.matcher(email);

        if (email != null && email.length() > 0 && !matcher.matches()) {
            FacesMessage message = new FacesMessage();
            message.setDetail(i18n("sharkness.email.validate.exception"));
            message.setSummary(i18n("sharkness.email.validate.exception"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }


    }

}
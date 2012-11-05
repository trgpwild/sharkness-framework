package org.sharkness.jsf.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.sharkness.helper.BrasilValidation;

public class CpfValidator extends ShkValidator {

	@Override
	public void validate(FacesContext context, UIComponent c, Object val) throws ValidatorException {

		String cpf = (String) val;

		if (cpf != null && cpf.length() > 0) {

			if (!BrasilValidation.isCpf(ignoreMasks(cpf))) {

				FacesMessage message = new FacesMessage();

				message.setDetail(i18n("sharkness.cpf.validate.exception"));

				message.setSummary(i18n("sharkness.cpf.validate.exception"));

				message.setSeverity(FacesMessage.SEVERITY_ERROR);

				throw new ValidatorException(message);

			}

		}

	}

}
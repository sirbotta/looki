/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.helper;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
/**
 *
 * @author Samuele
 */
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String password = value.toString();

        UIInput uiInputConfirmEmail = (UIInput) component.getAttributes()
                .get("confirmEmail");
        String confirmEmail = uiInputConfirmEmail.getSubmittedValue()
                .toString();

        // Let required="true" do its job.
        if (password == null || password.isEmpty() || confirmEmail == null
                || confirmEmail.isEmpty()) {
            return;
        }

        if (!password.equals(confirmEmail)) {
            uiInputConfirmEmail.setValid(false);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "ATTENZIONE",
                    "Password must match confirm password."));
        }

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.helper;

import java.sql.Timestamp;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author simone
 */
@FacesConverter("dueDateConverter")
public class DueDateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        Timestamp due_date = Timestamp.valueOf(value);

        return DataTool.verboseTillDate(due_date);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {

        Timestamp due_date = (Timestamp) value;

        return DataTool.verboseTillDate(due_date);

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.helper;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class DestroyCustomScope implements ActionListener {

	@Override
	public void processAction(final ActionEvent event) throws AbortProcessingException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();

		CustomScope customScope = (CustomScope) sessionMap.get(CustomScope.SCOPE_NAME);
		customScope.notifyDestroy(facesContext);

		sessionMap.remove(CustomScope.SCOPE_NAME);

	}

}

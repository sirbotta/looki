/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.helper;

import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

/**
 *
 * @author simone
 */
@ManagedBean
@ApplicationScoped
public class CustomScopeEnabler {

    /**
     * Creates a new instance of CustomScopeEnabler
     */
    public void enableScope(ComponentSystemEvent event){
 
	FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();

		CustomScope customScope = new CustomScope();
		sessionMap.put(CustomScope.SCOPE_NAME, customScope);

		customScope.notifyCreate(facesContext);
			
  }
    
    public void disableScope(ComponentSystemEvent event){
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();

		CustomScope customScope = (CustomScope) sessionMap.get(CustomScope.SCOPE_NAME);
		customScope.notifyDestroy(facesContext);

		sessionMap.remove(CustomScope.SCOPE_NAME);
    
    }
}

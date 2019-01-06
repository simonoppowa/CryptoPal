package de.othr.cryptopal.ui.model;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class AbstractModel implements Serializable {

    protected void addWarningMessage(String message, String component) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(component, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "INFO", getMessage(context, message)));
    }

    protected void addErrorMessage(String message, String component) {

    }

    private String getMessage(FacesContext facesContext, String msgKey) {
        Locale locale = facesContext.getViewRoot().getLocale();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale, classLoader);
        if(bundle.containsKey(msgKey)) {
            return bundle.getString(msgKey);
        } else {
            System.out.println("No message found in messages with key: " + msgKey);
            return "Error";
        }
    }
}

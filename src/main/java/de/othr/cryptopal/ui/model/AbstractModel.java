package de.othr.cryptopal.ui.model;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractModel implements Serializable {

    @Inject
    protected Logger logger;

    protected void addInfoMessage(String message, String component) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO,
                getMessage(context, "success"), getMessage(context, message)));
    }

    protected void addWarningMessage(String message, String component) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(component, new FacesMessage(FacesMessage.SEVERITY_WARN,
                getMessage(context, "info"), getMessage(context, message)));
    }

    protected void addErrorMessage(String message, String component) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                getMessage(context, "error"), getMessage(context, message)));

    }

    private String getMessage(FacesContext facesContext, String msgKey) {
        Locale locale = facesContext.getViewRoot().getLocale();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale, classLoader);
        if(bundle.containsKey(msgKey)) {
            return bundle.getString(msgKey);
        } else {
            logger.log(Level.WARNING, "No message found in messages with key: " + msgKey);
            return bundle.getString("default_info_message");
        }
    }
}

package de.othr.cryptopal.ui.model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;


@RequestScoped
@Named
public class CredentialsModel implements Serializable {

    private String email;
    private String password;
    private String confirmPassword;

    public CredentialsModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

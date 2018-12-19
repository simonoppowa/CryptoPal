package de.othr.cryptopal.ui.model;

import de.othr.cryptopal.service.InitService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
@ManagedBean
public class SampleModel implements Serializable {

    private String helloWorld;

    @Inject
    private InitService initService;

    public void init() {
        initService.init();
    }

    public String getHelloWorld() {
        return helloWorld;
    }

    public void setHelloWorld() {
        this.helloWorld = "Hello World";
    }
}

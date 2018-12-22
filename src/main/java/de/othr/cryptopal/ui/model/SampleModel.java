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

    @Inject
    private InitService initService;

    public void init() {
        initService.init();
    }
}

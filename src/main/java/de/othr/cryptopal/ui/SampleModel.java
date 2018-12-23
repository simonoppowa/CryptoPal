package de.othr.cryptopal.ui;

import de.othr.cryptopal.service.InitService;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
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

package de.othr.cryptopal.service;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
@ManagedBean
public class InitService {

    @PersistenceContext
    private EntityManager em;

    public void init() {
        System.out.println("Hello World");
    }
}

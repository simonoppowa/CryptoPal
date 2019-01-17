package de.othr.cryptopal.converter;

import de.othr.cryptopal.entity.Currency;
import de.othr.cryptopal.service.CurrencyInformationService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter("CurrencyConverter")
public class CurrencyConverter implements Converter {

    @Inject
    private CurrencyInformationService currencyInformationService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String pk) {
        if(pk == null) {
            return "";
        }
        Currency currency = currencyInformationService.getCurrencyFromMap(pk);
        if(currency == null) {
            return "";
        }
        return currency;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if(value == null) {
            return null;
        }
        if(!value.getClass().equals(Currency.class)) {
            return null;
        }
        return ((Currency)value).getCurrencyName();
    }
}

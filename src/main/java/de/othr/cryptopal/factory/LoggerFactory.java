package de.othr.cryptopal.factory;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

@Dependent
public class LoggerFactory {

    @Produces
    public Logger getLogger(InjectionPoint point) {
        return Logger.getLogger(point.getMember().getDeclaringClass().getCanonicalName());
    }
}

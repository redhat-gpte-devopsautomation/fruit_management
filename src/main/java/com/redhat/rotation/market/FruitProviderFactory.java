package com.redhat.rotation.market;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.redhat.rotation.market.service.FruitService;
import com.redhat.rotation.market.service.FruitServiceEphemeral;
import com.redhat.rotation.market.service.FruitServiceMongo;

@Provider
public class FruitProviderFactory {
    FruitService fruitService;

    public FruitProviderFactory() { } 

    /**
     * By default the memory based storage will be used.
     * 
     * @param ephemeral true if we want to use a simple list to store the fruits,
     *                  otherwise it would use a MongoDB instance.
     */

    @ConfigProperty(name = "fruit.storage.ephemeral", defaultValue = "true")
    javax.enterprise.inject.Instance<Boolean> ephemeral;

    @PostConstruct
    public void setup() {
        fruitService = getFruitService(ephemeral.get().booleanValue());        
    }    

    @Produces
    @ApplicationScoped
    FruitService fruitService() {
        return fruitService;
    }

    private FruitService getFruitService(boolean ephemeral) {
        // depending on the flag, a different implementation is chosen.
        return ephemeral ? new FruitServiceEphemeral() : new FruitServiceMongo();
    }
}

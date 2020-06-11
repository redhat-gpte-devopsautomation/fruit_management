package com.redhat.rotation.market.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.rotation.market.model.Fruit;

/**
 * Implementation that uses a MongoDB to store the Fruits. This implementation
 * is enabled by using an environment variable set to false.
 * {@code -Dfruit.storage.ephemeral=false}
 * 
 * @author armandorivas
 *
 */
public class FruitServiceMongo implements FruitService {
    private final Logger log = LoggerFactory.getLogger(FruitService.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public long createFruit(Fruit fruit) {
        log.debug("Creating a new mongo fruit with: {}", fruit);
        // creates a random id
        long id = new Random().nextInt(10000);
        fruit.setId(id);
        fruit.persist();
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Fruit> findAllFruits() {
        log.debug("Retrieving all the mongo fruits...");
        return Fruit.list("{}");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Fruit> findFruitById(long id) {
        return Fruit.findByIdOptional(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Fruit> updateFruitById(long id, Fruit fruit) {
        Optional<Fruit> found = findFruitById(id);
        if (found.isPresent()) {
            // performs the update
            found.get().setDescription(fruit.getDescription());
            found.get().setName(fruit.getName());
            found.get().setQuantity(fruit.getQuantity());

            found.get().update();
        }
        return found;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteFruitById(long id) {
        // performs the deletion
        Optional<Fruit> found = findFruitById(id);
        if (found.isPresent()) {
            found.get().delete();
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll() {
        // removes all the elements
        Fruit.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String findEnv() {
        // return the env.
        return this.getClass().getName();
    }
}

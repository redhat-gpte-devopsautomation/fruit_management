package com.redhat.rotation.market.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.rotation.market.model.Fruit;

/**
 * Default implementation that uses a simple list to store Fruits of this
 * application.
 * 
 * @author armandorivas
 *
 */
public class FruitServiceEphemeral implements FruitService {
    private final Logger log = LoggerFactory.getLogger(FruitService.class);

    /**
     * This list stores temporally info and it's initialized to have zero elements.
     */
    private List<Fruit> allFruits = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public long createFruit(Fruit fruit) {
        log.debug("Creating a new fruit with: {}", fruit);
        // creates a random id
        long id = new Random().nextInt(10000);
        fruit.setId(id);
        allFruits.add(fruit);
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Fruit> findAllFruits() {
        return allFruits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Fruit> findFruitById(long id) {
        return allFruits.stream().filter($ -> $.getId() == id).findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Fruit> updateFruitById(long id, Fruit fruit) {
        // find the Fruit first...
        Optional<Fruit> found = findFruitById(id);
        if (found.isPresent()) {
            // performs the update
            found.get().setDescription(fruit.getDescription());
            found.get().setName(fruit.getName());
            found.get().setQuantity(fruit.getQuantity());
        }
        return found;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteFruitById(long id) {
        // performs the deletion
        return allFruits.removeIf($ -> $.getId() == id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll() {
        // removes all the elements
        allFruits.clear();
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

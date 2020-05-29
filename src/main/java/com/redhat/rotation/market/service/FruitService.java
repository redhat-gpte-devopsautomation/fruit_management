package com.redhat.rotation.market.service;

import com.redhat.rotation.market.model.Fruit;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@ApplicationScoped
public class FruitService {
    private List<Fruit> allFruits = new ArrayList<>();

    public void createFruit(Fruit fruit) {
        // creates a random id
        fruit.setId(new Random().nextInt(10000));
        allFruits.add(fruit);
    }

    public List<Fruit> findAllFruits() {
        return allFruits;
    }

    public Optional<Fruit> findFruitById(long id) {
        return allFruits.stream().filter($ -> $.getId() == id).findFirst();
    }

    public Optional<Fruit> updateFruitById(long id, Fruit fruit) {
        Optional<Fruit> found = findFruitById(id);
        if (found.isPresent()) {
            // performs the update
            found.get().setDescription(fruit.getDescription());
            found.get().setName(fruit.getName());
            found.get().setQuantity(fruit.getQuantity());
        }
        return found;
    }

    public boolean deleteFruitById(long id) {
        // performs the deletion
        return allFruits.removeIf($ -> $.getId() == id);
    }
}

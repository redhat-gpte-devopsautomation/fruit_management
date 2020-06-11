package com.redhat.rotation.market.service;

import java.util.List;
import java.util.Optional;

import com.redhat.rotation.market.model.Fruit;

/**
 * Generic type used by different implementations to perform CRUD operations.
 * 
 * @author armandorivas
 *
 */
public interface FruitService {
    /**
     * Creates a new Fruit.
     * 
     * @param fruit The fruit to be created.
     */
    void createFruit(Fruit fruit);

    /**
     * Finds a list of all the Fruits available in the service. When no fruits exist
     * then an empty list will be returned.
     * 
     * @return List of fruits.
     */
    List<Fruit> findAllFruits();

    /**
     * Finds a specific Fruit based on it's id.
     * 
     * @param id The unique identifier for a Fruit.
     * @return A valid fruit or nothing if it was not found.
     */
    Optional<Fruit> findFruitById(long id);

    /**
     * Updates a Fruit by its identifier. This operation replaces the entire object.
     * No partial updates will be performed.
     * 
     * @param id    The unique identifier for a Fruit.
     * @param fruit The new fruit to be used as replacement. This object contains
     *              the new values of the fruit.
     * @return A valid updated fruit or nothing if it was not found.
     */
    Optional<Fruit> updateFruitById(long id, Fruit fruit);

    /**
     * Deletes an existing Fruit based on its id.
     * 
     * @param id The unique identifier for a Fruit.
     * @return true if the fruit was deleted, otherwise false.
     */
    boolean deleteFruitById(long id);

    /**
     * Deletes all the existing Fruits.
     */
    void deleteAll();

    /**
     * Returns the current implementation.
     * 
     * @return The current Fruit implementation used.
     */
    String findEnv();
}
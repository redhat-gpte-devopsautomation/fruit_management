package com.redhat.rotation.market.controllers;

import com.redhat.rotation.market.model.Fruit;
import com.redhat.rotation.market.service.FruitService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.mockito.InjectMock;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@QuarkusTest
public class FruitResourceTest {

    @InjectMock
    FruitService fruitService;

    @Test
    public void createFruit_ok() {
        Fruit newFruit = createFruit("Apple", "A simple apple", 3);

        given().contentType(ContentType.JSON)
                .body(newFruit)
                .post("market/fruits")
                .then()
                .statusCode(204);
    }

    @Test
    public void findAllFruits_ok() {
        List<Fruit> fruitFound = new ArrayList<>();
        fruitFound.add(createFruit("Apple", "A simple apple", 3));
        fruitFound.add(createFruit("Orange", "A simple orange", 6));

        when(fruitService.findAllFruits()).thenReturn(fruitFound);

        given().get("market/fruits").then().statusCode(200);
        List<Fruit> list = given().get("market/fruits").then().extract().body().jsonPath().getList(".", Fruit.class);

        assertEquals(fruitFound.size(), list.size());
    }

    private Fruit createFruit(String name, String description, int quantity) {
        Fruit newFruit = new Fruit();
        newFruit.setQuantity(quantity);
        newFruit.setName(name);
        newFruit.setDescription(description);
        return newFruit;
    }
}
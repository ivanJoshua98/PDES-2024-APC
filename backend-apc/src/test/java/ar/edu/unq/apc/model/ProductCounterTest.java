package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductCounterTest {

    private ProductCounter anyCounter;

    @BeforeEach
    private void init(){
        this.anyCounter = new ProductCounter();
    }

    @Test
    void whenItsSetsTheFavoriteCounterThenItsReturnsANewFavoriteCounterTest() {
        anyCounter.setFavoriteCounter(2);

        assertEquals(2, anyCounter.getFavoriteCounter());
    }

    @Test
    void whenItsSetsThePictureThenItsReturnsANewPictureTest() {
        anyCounter.setPicture("https://picture1.com");

        assertEquals("https://picture1.com", anyCounter.getPicture());
    }

    @Test
    void whenItsSetsTheProductIdThenItsReturnsANewProductIdTest() {
        anyCounter.setProductId("MLA23456789");

        assertEquals("MLA23456789", anyCounter.getProductId());
    }

    @Test
    void whenItsSetsTheTitleThenItsReturnsANewTitleTest() {
        anyCounter.setTitle("Producto generico");

        assertEquals("Producto generico", anyCounter.getTitle());
    }

    @Test
    void whenItsAddsAFavoriteAmountThenItsReturnsANewTotalTest() {
        anyCounter.setFavoriteCounter(3);

        anyCounter.addAmountOfFavorites(1);

        assertEquals(4, anyCounter.getFavoriteCounter());
    }
}

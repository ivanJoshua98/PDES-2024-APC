package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MercadoLibreProductTest {

    private MercadoLibreProduct anyProduct;

    @BeforeEach
    public void init(){
        this.anyProduct = new MercadoLibreProduct();
    }
    

    @Test
    void whenItSetsTheIdOfAnyMercadoLibreProductThenItReturnsANewIdTest() {
        anyProduct.setId("MLA343243");

        assertEquals(anyProduct.getId(), "MLA343243");
    }

    @Test
    void whenItSetsThePicturesOfAnyMercadoLibreProductThenItReturnsANewListOfPicturesTest() {
        List<String> pictures = List.of("https://picture1", "https://picture2" );

        anyProduct.setPictures(pictures);

        assertTrue(anyProduct.getPictures().containsAll(pictures));
    }   

}

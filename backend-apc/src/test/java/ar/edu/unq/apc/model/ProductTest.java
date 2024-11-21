package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {

    private Product anyProduct;

    @BeforeEach
    public void init(){
        this.anyProduct = new Product();
    }
    

    @Test
    void whenItSetsTheCategoryIdOfAnyProductThenItReturnsANewCategoryIdTest() {
        anyProduct.setCategoryId("MLA45632345");

        assertEquals(anyProduct.getCategoryId(), "MLA45632345");
    }

    @Test
    void whenItSetsTheConditiondOfAnyProductThenItReturnsANewConditionTest() {
        anyProduct.setCondition("new");

        assertEquals(anyProduct.getCondition(), "new");
    }



    @Test
    void whenItSetsTheLinkOfAnyProductThenItReturnsANewLinkTest() {
        anyProduct.setLink("https://mercadoLibre.com/MLA2232123");

        assertEquals(anyProduct.getLink(), "https://mercadoLibre.com/MLA2232123");
    }


    @Test
    void whenItSetsThePriceOfAnyProductThenItReturnsANewPriceTest() {
        anyProduct.setPrice(2500.50);

        assertEquals(anyProduct.getPrice(), 2500.50);
    }

    @Test
    void whenItSetsTheTitleOfAnyProductThenItReturnsANewTitleTest() {
        anyProduct.setTitle("Cafe Marolio, le da sabor a tu vida");

        assertEquals(anyProduct.getTitle(), "Cafe Marolio, le da sabor a tu vida");
    }
}

package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductInCartTest {
    
    private ProductInCart anyProduct;

    @BeforeEach
    public void init(){
        this.anyProduct = new ProductInCart();
    }
    

    @Test
    void whenItSetsTheAmountOfAnyProductInCartThenItReturnsANewAmountTest() {
        anyProduct.setAmount(2);

        assertEquals(anyProduct.getAmount(), 2);
    }

    @Test
    void whenItSetsTheIdOfAnyProductInCartThenItReturnsANewIdTest() {
        UUID id = UUID.randomUUID();
        anyProduct.setId(id);

        assertEquals(anyProduct.getId(), id);
    }

    @Test
    void whenItSetsTheMercadoLibreIdOfAnyProductInCartThenItReturnsANewMercadoLibreTest() {
        anyProduct.setMercadoLibreId("MLA564234");

        assertEquals(anyProduct.getMercadoLibreId(), "MLA564234");
    }

    @Test
    void whenItSetsThePictureOfAnyProductInCartThenItReturnsANewPictureTest() {
        anyProduct.setPicture("https://picture");

        assertEquals(anyProduct.getPicture(), "https://picture");
    }

    @Test
    void whenItSubtractsAnAmountOneTimeOfProductInCartWithAnAmountMajorToOneThenItReturnsTheAmountMinusOne() {
        anyProduct.setAmount(2);
        anyProduct.subtractAmountOneTime();

        assertEquals(anyProduct.getAmount(), 1);
    }

    void whenItSubtractsAnAmountOneTimeOfProductInCartWithAnAmountEqualToOneThenItDoesntUpdateAmount() {
        anyProduct.setAmount(1);
        anyProduct.subtractAmountOneTime();

        assertEquals(anyProduct.getAmount(), 1);
    }

    @Test
    void whenItAddsAnAmountOneTimeOfProductInCartThenItReturnsTheAmountPlusOne() {
        anyProduct.setAmount(1);
        anyProduct.addAmountOneTime();

        assertEquals(anyProduct.getAmount(), 2);
    }

    @Test
    void whenItEqualsAnyProductInCartThenItReturnsTrueIfTheIdsAreEqual() {
        UUID id = UUID.randomUUID();
        anyProduct.setId(id);

        ProductInCart otheProduct = new ProductInCart();
        otheProduct.setId(id);

        assertEquals(anyProduct, otheProduct);
    }

    @Test
    void whenItEqualsAnyProductInCartThenItReturnsFalseIfTheIdsArentEqual() {
        anyProduct.setId(UUID.randomUUID());

        ProductInCart otheProduct = new ProductInCart();
        otheProduct.setId(UUID.randomUUID());

        assertNotEquals(anyProduct, otheProduct);
    }

}

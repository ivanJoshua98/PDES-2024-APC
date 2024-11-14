package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseTest {

    private Purchase anyPurchase;

    @BeforeEach
    public void init(){
        this.anyPurchase = new Purchase();
    }
    

    @Test
    void whenItSetsTheBuyerOfAnyPurchaseThenItReturnsANewBuyerTest() {
        UserModel anyUser = new UserModel();
        anyUser.setId(UUID.randomUUID());

        anyPurchase.setBuyer(anyUser);

        assertEquals(anyPurchase.getBuyer().getId(), anyUser.getId());
    }

    @Test
    void whenItSetsTheIdOfAnyPurchaseThenItReturnsANewIdTest() {
        UUID randomId = UUID.randomUUID();
        anyPurchase.setId(randomId);

        assertEquals(anyPurchase.getId(), randomId);
    }

    @Test
    void whenItSetsTheSalePriceOfAnyProductThenItReturnsANewSalePriceTest() {
        anyPurchase.setSalePrice(24000.45);

        assertEquals(anyPurchase.getSalePrice(), 24000.45);
    }

    @Test
    void whenItSetsTheProductsSoldListOfAnyProductThenItReturnsANewListOfProductsSoldTest() {
        List<Product> soldProducts = List.of(new Product(), new Product());

        anyPurchase.setSoldProducts(soldProducts);

        assertTrue(anyPurchase.getSoldProducts().containsAll(soldProducts));
    }

}

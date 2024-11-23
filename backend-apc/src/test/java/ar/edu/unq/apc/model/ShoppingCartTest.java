package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class ShoppingCartTest {

    private ShoppingCart anyShoppingCart;

    private ProductInCart anyProduct;

    @BeforeEach
    public void init(){
        this.anyShoppingCart = new ShoppingCart();
        this.anyProduct = new ProductInCart();
        anyProduct.setAmount(1);
        anyProduct.setPrice(1000.00);
    }

    @Test
    void whenItSetsTheBuyerOfAnyShoppingCartThenItReturnsANewBuyerTest() {
        UserModel user = new UserModel();
        anyShoppingCart.setBuyer(user);

        assertEquals(anyShoppingCart.getBuyer(), user);
    }

    @Test
    void whenItSetsTheListOfProductsOfAnyShoppingCartThenItReturnsANewListTest() {
        List<ProductInCart> products = List.of(anyProduct);

        anyShoppingCart.setCart(products);

        assertTrue(anyShoppingCart.getCart().containsAll(products));
    }

    @Test
    void whenItSetsTheStateOfAnyShoppingCartThenItReturnsANewStateTest() {
        anyShoppingCart.setCartState(CartState.SOLD);

        assertEquals(anyShoppingCart.getCartState(), CartState.SOLD);
    }

    @Test
    void whenItSetsTheIdOfAnyShoppingCartThenItReturnsANewIdTest() {
        UUID id = UUID.randomUUID();

        anyShoppingCart.setId(id);

        assertEquals(anyShoppingCart.getId(), id);
    }

    @Test
    void whenItSetsTheTotalAmountPurchaseOfAnyShoppingCartThenItReturnsANewTotalTest() {
        anyShoppingCart.setTotalAmountPurchase(20000.00);

        assertEquals(anyShoppingCart.getTotalAmountPurchase(), 20000.00);
    }

    @Test
    void whenItAddsAProductThatDoesntExistsInShoppingCartThenItReturnsTheNewListOfProducts() {        
        anyShoppingCart.addProduct(anyProduct);

        assertTrue(anyShoppingCart.getCart().contains(anyProduct));
    }

    @Test
    void whenItAddsAProductThatDoesntExistsInShoppingCartThenItUpdatesTheTotalAmountPurchase() {
        anyProduct.setPrice(2000.00);

        anyShoppingCart.addProduct(anyProduct);

        assertEquals(anyShoppingCart.getTotalAmountPurchase(), 2000.00);
    }

    @Test
    void whenItAddsOneTimeAProductThatAlreadyExistsInShoppingCartThenItReturnsTheAmountPlusOne() {
        anyShoppingCart.addProduct(anyProduct);

        anyShoppingCart.addProductOneTime(anyProduct);

        Integer index = anyShoppingCart.getCart().indexOf(anyProduct);
        assertEquals(anyShoppingCart.getCart().get(index).getAmount(), 2);
    }

    @Test
    void whenItAddsOneTimeAProductThatAlreadyExistsInShoppingCartThenItUpdatedTheTotalAmountPurchase() {
        anyProduct.setPrice(4000.00);
        anyShoppingCart.addProduct(anyProduct);
        
        anyShoppingCart.addProductOneTime(anyProduct);

        assertEquals(anyShoppingCart.getTotalAmountPurchase(), 8000.00);;
    }

    @Test
    void whenItRemovesAProductOfShoppingCartThenItUpdatesTheListOfProducts() {
        anyShoppingCart.addProduct(anyProduct);

        anyShoppingCart.removeProduct(anyProduct);

        assertFalse(anyShoppingCart.getCart().contains(anyProduct));
    }

    @Test
    void whenItRemovesAProductOfShoppingCartThenItUpdatesTheTotalAmountPurchase() {
        anyShoppingCart.addProduct(anyProduct);

        anyShoppingCart.removeProduct(anyProduct);

        assertEquals(anyShoppingCart.getTotalAmountPurchase(), 0.0);
    }

    @Test
    void whenItSubtractsOneTimeAProductOfShoppingCartThenItUpdatesTheTotalAmountPurchase() {
        anyProduct.setPrice(2000.00);
        anyProduct.setAmount(4);     
        anyShoppingCart.addProduct(anyProduct);

        anyShoppingCart.subtractProductOneTime(anyProduct);

        assertEquals(anyShoppingCart.getTotalAmountPurchase(), 6000.00);
    }

    @Test
    void whenItSubtractsOneTimeAProductWithAmountEqualToOneThenItDoesntUpdateTotalAmountPurchase(){
        anyProduct.setPrice(5000.00);
        anyProduct.setAmount(1);     
        anyShoppingCart.addProduct(anyProduct);

        anyShoppingCart.subtractProductOneTime(anyProduct);

        assertEquals(anyShoppingCart.getTotalAmountPurchase(), 5000.00);
    }

    @Test
    void whenItSubtractsOneTimeAProductOfShoppingCartThenItUpdatesTheAmountOfProduct() {
        anyProduct.setAmount(4);     
        anyShoppingCart.addProduct(anyProduct);

        anyShoppingCart.subtractProductOneTime(anyProduct);

        Integer index = anyShoppingCart.getCart().indexOf(anyProduct);
        assertEquals(anyShoppingCart.getCart().get(index).getAmount(), 3);
    }
}

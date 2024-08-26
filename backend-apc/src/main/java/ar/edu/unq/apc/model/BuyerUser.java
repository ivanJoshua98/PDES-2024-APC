package ar.edu.unq.apc.model;
import java.util.List;

public class BuyerUser extends User {
    private List<Product> favoriteProducts;
    private List<Buy> purchasesMade;

    public BuyerUser(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    public List<Product> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void addFavoriteProducts(Product product){
        favoriteProducts.add(product);
    }

    public void deleteFavoriteProducts(Product product){
        favoriteProducts.remove(product);
    }

    public List<Buy> getPurchasesMade() {
        return purchasesMade;
    }

    //agregar compra realizada
    public void addBuy(Buy purchaseMade){
        purchasesMade.add(purchaseMade);
    }


}
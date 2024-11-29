package ar.edu.unq.apc.service;

import java.util.List;
import java.util.UUID;

import ar.edu.unq.apc.model.FavoriteProductInTop;
import ar.edu.unq.apc.model.ProductsPurchasedByUserCounter;
import ar.edu.unq.apc.model.PurchasedProductCounter;
import ar.edu.unq.apc.model.PurchasedProductInTop;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.model.UserWithMostPurchases;

public interface SystemReportService {
    
    List<UserWithMostPurchases> getUsersWithMostPurchases();

    List<PurchasedProductInTop> getMostPurchasedProducts();

    List<FavoriteProductInTop> getFavoriteProductsTopFive();

    List<UserWithMostPurchases> getUsersWithMostPurchasedProducts();

    ProductsPurchasedByUserCounter saveProductsPurchasedByUserCounter(UserModel buyer, Integer amount);

    ProductsPurchasedByUserCounter getProductsPurchasedByUserCounterByUserId(UUID userId);

    PurchasedProductCounter getPurchasedProductCounterByProductId(String productId);

    PurchasedProductCounter savePurchasedProductCounter(String mercadoLibreId, Integer amount);

}

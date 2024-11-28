package ar.edu.unq.apc.service;

import java.util.List;
import java.util.UUID;

import ar.edu.unq.apc.model.FavoriteProductInTopFive;
import ar.edu.unq.apc.model.MercadoLibreProduct;
import ar.edu.unq.apc.model.ProductsPurchasedByUserCounter;
import ar.edu.unq.apc.model.PurchasedProductCounter;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.model.UserWithMostPurchases;

public interface SystemReportService {
    
    List<UserWithMostPurchases> getUsersWithMostPurchases();

    List<MercadoLibreProduct> getMostPurchasedProducts();

    List<FavoriteProductInTopFive> getFavoriteProductsTopFive();

    PurchasedProductCounter savePurchasedProductCounter(PurchasedProductCounter counter);

    ProductsPurchasedByUserCounter saveProductsPurchasedByUserCounter(UserModel buyer, Integer amount);

    ProductsPurchasedByUserCounter getProductsPurchasedByUserCounterByUserId(UUID userId);

    List<UserWithMostPurchases> getUsersWithMostPurchasedProducts();

}

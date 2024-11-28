package ar.edu.unq.apc.service;

import java.util.List;

import ar.edu.unq.apc.model.MercadoLibreProduct;
import ar.edu.unq.apc.model.ProductsPurchasedByUserCounter;
import ar.edu.unq.apc.model.PurchasedProductCounter;
import ar.edu.unq.apc.model.UserWithMostPurchases;

public interface SystemReportService {
    
    List<UserWithMostPurchases> getUsersWithMostPurchases();

    List<MercadoLibreProduct> getMostPurchasedProducts();

    List<MercadoLibreProduct> getFavoriteProductsTopFive();

    PurchasedProductCounter savePurchasedProductCounter(PurchasedProductCounter counter);

    ProductsPurchasedByUserCounter saveProductsPurchasedByUserCounter(ProductsPurchasedByUserCounter counter);

}

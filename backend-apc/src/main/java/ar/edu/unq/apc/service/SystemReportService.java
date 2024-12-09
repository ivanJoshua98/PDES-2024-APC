package ar.edu.unq.apc.service;

import java.util.List;
import java.util.UUID;

import ar.edu.unq.apc.model.ProductCounter;
import ar.edu.unq.apc.model.UserCounter;

public interface SystemReportService {
    
    List<UserCounter> getUsersWithMostPurchases(Integer limit);

    List<ProductCounter> getMostPurchasedProducts(Integer limit);

    List<ProductCounter> getMostFavorites(Integer limit);

    List<UserCounter> getUsersWithMostPurchasesByProducts(Integer limit);

    ProductCounter saveProductCounter(ProductCounter counter);

    UserCounter saveUserCounter(UserCounter counter);

    ProductCounter getProductCounter(String productId);

    UserCounter getUserCounter(UUID userId);

}

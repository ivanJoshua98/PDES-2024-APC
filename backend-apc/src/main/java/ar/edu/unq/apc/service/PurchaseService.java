package ar.edu.unq.apc.service;

import java.util.List;
import java.util.UUID;

import ar.edu.unq.apc.model.Purchase;
import ar.edu.unq.apc.model.UserModel;

public interface PurchaseService {
    
    Purchase savePurchase(Purchase puerchase);

    List<Purchase> getAllPurchases();

    Purchase getPurchaseById(UUID id);

    Purchase getPurchaseByBuyer(UserModel buyer);

}

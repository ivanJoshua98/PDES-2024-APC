package ar.edu.unq.apc.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ar.edu.unq.apc.model.FavoriteProductInTopFive;
import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.MercadoLibreProduct;
import ar.edu.unq.apc.model.ProductsPurchasedByUserCounter;
import ar.edu.unq.apc.model.PurchasedProductCounter;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.model.UserWithMostPurchases;
import ar.edu.unq.apc.persistence.ProductsPurchasedByUserCounterRepository;
import ar.edu.unq.apc.persistence.PurchasedProductCounterRepository;
import ar.edu.unq.apc.service.SystemReportService;
import ar.edu.unq.apc.service.UserService;

@Service
public class SystemReportsServiceImpl implements SystemReportService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductsPurchasedByUserCounterRepository counterByUserRepository;

    @Autowired
    private PurchasedProductCounterRepository purchasedProductCounterRepository;

    @Override
    public List<UserWithMostPurchases> getUsersWithMostPurchases() {    
        return this.userService.getUserWithMostPurchases();
    }

    @Override
    public List<MercadoLibreProduct> getMostPurchasedProducts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMostPurchasedProducts'");
    }

    @Override
    public List<FavoriteProductInTopFive> getFavoriteProductsTopFive() {
        return this.counterByUserRepository.getFavoriteProductsTopFive();
    }

    @Override
    public PurchasedProductCounter savePurchasedProductCounter(PurchasedProductCounter counter) {
        return this.purchasedProductCounterRepository.save(counter);
    }

    @Override
    public ProductsPurchasedByUserCounter getProductsPurchasedByUserCounterByUserId(UUID userId) {
        return this.counterByUserRepository.findById(userId).orElseThrow(() -> new HttpException("Counter not found by user id: " + userId, HttpStatus.NOT_FOUND));
    }

    @Override
    public ProductsPurchasedByUserCounter saveProductsPurchasedByUserCounter(UserModel buyer, Integer amount) {
        try {
            ProductsPurchasedByUserCounter counter = getProductsPurchasedByUserCounterByUserId(buyer.getId());
            counter.countNewPurchasedProduct(amount);
            return this.counterByUserRepository.save(counter);
        } catch (HttpException e) {
            ProductsPurchasedByUserCounter newCounter = new ProductsPurchasedByUserCounter(buyer.getId(), amount);
            return this.counterByUserRepository.save(newCounter);
        }
    }

    @Override
    public List<UserWithMostPurchases> getUsersWithMostPurchasedProducts() {
        return this.counterByUserRepository.getUsersWithMostPurchasedProducts();
    }
    
}

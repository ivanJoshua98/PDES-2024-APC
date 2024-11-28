package ar.edu.unq.apc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.apc.model.MercadoLibreProduct;
import ar.edu.unq.apc.model.ProductsPurchasedByUserCounter;
import ar.edu.unq.apc.model.PurchasedProductCounter;
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
    public List<MercadoLibreProduct> getFavoriteProductsTopFive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFavoriteProductsTopFive'");
    }

    @Override
    public PurchasedProductCounter savePurchasedProductCounter(PurchasedProductCounter counter) {
        return this.purchasedProductCounterRepository.save(counter);
    }

    @Override
    public ProductsPurchasedByUserCounter saveProductsPurchasedByUserCounter(ProductsPurchasedByUserCounter counter) {
        return this.counterByUserRepository.save(counter);
    }
    
}

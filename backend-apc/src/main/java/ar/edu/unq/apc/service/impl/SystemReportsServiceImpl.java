package ar.edu.unq.apc.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.ProductCounter;
import ar.edu.unq.apc.model.UserCounter;
import ar.edu.unq.apc.persistence.ProductCounterRepository;
import ar.edu.unq.apc.persistence.UserCounterRepository;
import ar.edu.unq.apc.service.SystemReportService;

@Service
public class SystemReportsServiceImpl implements SystemReportService {

    @Autowired
    private ProductCounterRepository pcRepocitory;

    @Autowired
    private UserCounterRepository ucRepocitory;

    @Override
    public List<UserCounter> getUsersWithMostPurchases(Integer limit) {
        return this.ucRepocitory.getUsersWithMostPurchases(CartState.SOLD, limit);
    }

    @Override
    public List<ProductCounter> getMostPurchasedProducts(Integer limit) {
        return this.pcRepocitory.getMostPurchasedProducts(limit);
    }

    @Override
    public List<ProductCounter> getMostFavorites(Integer limit) {
        return this.pcRepocitory.getMostFavorites(limit);
    }

    @Override
    public List<UserCounter> getUsersWithMostPurchasesByProducts(Integer limit) {
        return this.ucRepocitory.getUsersWithMostPurchasesByProducts(limit);
    }

    @Override
    public ProductCounter saveProductCounter(ProductCounter counter) {
        return this.pcRepocitory.save(counter);
    }

    @Override
    public UserCounter saveUserCounter(UserCounter counter) {
        return this.ucRepocitory.save(counter);
    }

    @Override
    public ProductCounter getProductCounter(String productId) {
        return this.pcRepocitory.findById(productId).orElseThrow(() -> new HttpException("Counter not found by product id: " + productId, HttpStatus.NOT_FOUND));
    }

    @Override
    public UserCounter getUserCounter(UUID userId) {
        return this.ucRepocitory.findById(userId).orElseThrow(() -> new HttpException("Counter not found by user id: " + userId, HttpStatus.NOT_FOUND));
    }
}

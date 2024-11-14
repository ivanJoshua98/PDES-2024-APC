package ar.edu.unq.apc.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.Purchase;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.persistence.PurcharseRepository;
import ar.edu.unq.apc.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    
    @Autowired
    private PurcharseRepository purcharseRepository;

    @Override
    public Purchase savePurchase(Purchase purchase) {
        return this.purcharseRepository.save(purchase);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return this.purcharseRepository.findAll();
    }

    @Override
    public Purchase getPurchaseById(UUID id) {
        return this.purcharseRepository.findById(id).orElseThrow(() -> new HttpException("Purchase not found by id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public Purchase getPurchaseByBuyer(UserModel buyer) {
        return this.purcharseRepository.findByBuyer(buyer).orElseThrow(() -> new HttpException("Purchase not found by buyer", HttpStatus.NOT_FOUND));

    }
    
}

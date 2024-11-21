package ar.edu.unq.apc.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.ProductInCart;
import ar.edu.unq.apc.persistence.ProductInCartRepository;
import ar.edu.unq.apc.service.ProductInCartService;

@Service
public class ProductInCartServiceImpl implements ProductInCartService{


    @Autowired
    private ProductInCartRepository productRepository;
    

    @Override
    public ProductInCart saveProduct(ProductInCart newProduct) {
        return this.productRepository.save(newProduct);
    }

    @Override
    public ProductInCart getProductById(UUID id) {
        return this.productRepository.findById(id).orElseThrow(() -> new HttpException("Product not found by id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<ProductInCart> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public void deleteProductById(UUID id) {
        this.productRepository.deleteById(id);
    }
    
}

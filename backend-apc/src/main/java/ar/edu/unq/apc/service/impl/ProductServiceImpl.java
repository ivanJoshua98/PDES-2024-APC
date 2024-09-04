package ar.edu.unq.apc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.Product;
import ar.edu.unq.apc.persistence.ProductRepository;
import ar.edu.unq.apc.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    private ProductRepository productRepository;
    

    @Override
    public Product saveProduct(Product newProduct) {
        return this.productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(String id) {
        return this.productRepository.findById(id).orElseThrow(() -> new HttpException("Product not found by id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
    
}

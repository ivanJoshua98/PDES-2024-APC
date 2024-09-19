package ar.edu.unq.apc.service;

import java.util.List;

import ar.edu.unq.apc.model.Product;

public interface ProductService {

    Product saveProduct(Product newProduct);

    Product getProductById(String id);

    List<Product> getAllProducts();
    
}

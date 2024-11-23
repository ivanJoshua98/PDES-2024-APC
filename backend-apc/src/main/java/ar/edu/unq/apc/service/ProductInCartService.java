package ar.edu.unq.apc.service;

import java.util.List;
import java.util.UUID;

import ar.edu.unq.apc.model.ProductInCart;

public interface ProductInCartService {

    ProductInCart saveProduct(ProductInCart newProduct);

    ProductInCart getProductById(UUID id);

    List<ProductInCart> getAllProducts();

    void deleteProductById(UUID id);

    ProductInCart getProductByMercadoLibreId(String id);
    
}

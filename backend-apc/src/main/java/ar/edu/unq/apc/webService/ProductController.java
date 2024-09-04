package ar.edu.unq.apc.webService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.apc.model.Product;
import ar.edu.unq.apc.service.MercadoLibreProxyService;
import ar.edu.unq.apc.webService.dto.ProductDTO;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private MercadoLibreProxyService mercadoLibre;

    
    @GetMapping("/search/{words}")
	public ResponseEntity<List<ProductDTO>> searchProductsByWorks(@PathVariable String words){
		List <Product> products = this.mercadoLibre.searchProductsByWords(words);
		return ResponseEntity.ok()
							 .body(products.stream()
							               .map(this::convertProductEntityToProductDTO)
                                           .toList());
	}


    private ProductDTO convertProductEntityToProductDTO(Product product){
        return new ProductDTO(product.getId(),
                            product.getLink(),
                            product.getTitle(),
                            product.getCategoryId(),
                            product.getPrice(),
                            product.getPictures(),
                            product.getAttributes(),
                            product.getCondition()
                            );
    }
     
    
}

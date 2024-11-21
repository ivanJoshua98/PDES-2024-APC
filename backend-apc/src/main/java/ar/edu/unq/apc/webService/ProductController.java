package ar.edu.unq.apc.webService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.apc.model.MercadoLibreProduct;
import ar.edu.unq.apc.service.MercadoLibreProxyService;
import ar.edu.unq.apc.webService.dto.MercadoLibreProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product services", description = "Manage products of the application")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private MercadoLibreProxyService mercadoLibre;

    
    @Operation(summary = "Get products from search by key words")
    @GetMapping("/search/{words}")
	public ResponseEntity<List<MercadoLibreProductDTO>> searchProductsByWorks(
        @Parameter(description = "The key words that needs to be searched", required = true)
        @PathVariable String words){
		List<MercadoLibreProduct> products = this.mercadoLibre.searchProductsByWords(words);
		return ResponseEntity.ok()
							 .body(products.stream()
							               .map(this::convertProductEntityToProductDTO)
                                           .toList());
	}


    @Operation(summary = "Get product from Mercado Libre")
    @GetMapping("/search/item/{id}")
	public ResponseEntity<MercadoLibreProductDTO> getProductsByIdFromML(
        @Parameter(description = "The id that needs to be fetched", required = true)
        @PathVariable String id){
        
        MercadoLibreProduct product = this.mercadoLibre.getProductById(id);
        return ResponseEntity.ok()
                             .body(convertProductEntityToProductDTO(product));

    }

    private MercadoLibreProductDTO convertProductEntityToProductDTO(MercadoLibreProduct product){
        return new MercadoLibreProductDTO(product.getId(),
                            product.getLink(),
                            product.getTitle(),
                            product.getCategoryId(),
                            product.getPrice(),
                            product.getPictures(),
                            product.getCondition()
                            );
    }
     
    
}

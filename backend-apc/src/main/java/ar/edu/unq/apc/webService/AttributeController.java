package ar.edu.unq.apc.webService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.apc.model.Attribute;
import ar.edu.unq.apc.service.MercadoLibreProxyService;
import ar.edu.unq.apc.webService.dto.AttributeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product Attributes services", description = "Manage product attributes of the application")
@RestController
@RequestMapping("/attributes")
public class AttributeController {

    @Autowired
    private MercadoLibreProxyService mercadoLibre;
    

    @Operation(summary = "Get product attributes from Mercado Libre by product id")
    @GetMapping("/search/{productId}")
	public ResponseEntity<List<AttributeDTO>> searchProductAttributesByProductId(
        @Parameter(description = "The id that needs to be fetched", required = true)
        @PathVariable String productId){

        List<Attribute> attributes = this.mercadoLibre.getProductAttributes(productId);
        
        return ResponseEntity.ok().body(attributes.stream()
                                                  .map(this::convertAttributeEntityToAttributeDTO)
                                                  .toList());
    }


    public AttributeDTO convertAttributeEntityToAttributeDTO(Attribute attribute){
        return new AttributeDTO(attribute.getName(), attribute.getValue());
    }
}

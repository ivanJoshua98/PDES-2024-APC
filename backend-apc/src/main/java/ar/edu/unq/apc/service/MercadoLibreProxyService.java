package ar.edu.unq.apc.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ar.edu.unq.apc.model.Attribute;
import ar.edu.unq.apc.model.Product;

@Service
public class MercadoLibreProxyService {
    
    private RestTemplate restTemplate = new RestTemplate();

    //La busqueda en Mercado Libre solo permite 20 resultados
    private Integer limitOfResults = 20;

    @Value("${integration.mercadolibre.api.token:NONE}")
    private String token;

    @Value("${integration.mercadolibre.api.url:NONE}")
    private String mercadoLibreApiURL;


    public List<Product> searchProductsByWords(String search){
        String jsonResponseFromSearch = executeGetRequest(mercadoLibreApiURL + "/sites/MLA/search?q=" + search);
        List<String> productsId = getProductsIdFromSearchResults(jsonResponseFromSearch);
        return getProductsByIds(productsId);
    }


    public List<String> getProductsIdFromSearchResults(String json) {
        JsonParser parser = new JsonParser();

        // Obtain element
        JsonObject gsonObj = parser.parse(json).getAsJsonObject();
	    JsonArray results = gsonObj.get("results").getAsJsonArray();

	    List<String> listIds = new ArrayList<String>();
        //for (JsonElement result : results) {
        for (int i = 0; i < limitOfResults; i++){
            JsonElement result = results.get(i);
		    JsonObject gsonResult = result.getAsJsonObject();
		    String id = gsonResult.get("id").getAsString();
        	listIds.add(id);
	    }
        return listIds;
    }

    
    public List<Product> getProductsByIds( List<String> ids ){
        String idsToSearch = String.join(",", ids);
        String jsonResult = executeGetRequest(mercadoLibreApiURL + "/items?ids=" +
            idsToSearch + "&attributes=id,price,category_id,title,pictures,condition,permalink,attributes");

        JsonParser parser = new JsonParser();
        List<Product> products = new ArrayList<Product>();

        // Obtain Array
        JsonArray gsonArr = parser.parse(jsonResult).getAsJsonArray();

        for (JsonElement obj : gsonArr) {
            // Object of array
            JsonObject gsonObj = obj.getAsJsonObject();
            JsonObject body = gsonObj.get("body").getAsJsonObject();

            
            products.add(deserializeProduct(body));
        }
        return products;
    }
 
    
    public String executeGetRequest(String url){
        HttpEntity<Void> request;
        HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response.getBody();
    }
    
    public Product deserializeProduct(JsonObject gsonObj){
        String title = gsonObj.get("title").getAsString();
        String condition = getStringFromJson("condition", gsonObj);
        String id = gsonObj.get("id").getAsString();
        String categoryId = gsonObj.get("category_id").getAsString();
        Double price = getDoubleFromJson("price", gsonObj);
        String link = gsonObj.get("permalink").getAsString();
            
        JsonArray gsonArrPictures = gsonObj.get("pictures").getAsJsonArray();
        List<String> pictures = extractUrlPictures(gsonArrPictures);

        JsonArray gsonArrAtributes = gsonObj.get("attributes").getAsJsonArray();
        List<Attribute> attributes = extractAttributes(gsonArrAtributes);

        return new Product(id, link, title, categoryId, price, pictures, attributes, condition);
    }


    public List<String> extractUrlPictures(JsonArray gsonArr){
        List<String> pictures = new ArrayList<String>();
        for (JsonElement obj : gsonArr) {
            JsonObject gsonObj = obj.getAsJsonObject();
            String picture = gsonObj.get("secure_url").getAsString();
            pictures.add(picture);
        }
        return pictures;
    }


    public List<Attribute> extractAttributes(JsonArray gsonArr){
        List<Attribute> attributes = new ArrayList<Attribute>();
        for (JsonElement obj : gsonArr) {
            JsonObject gsonObj = obj.getAsJsonObject();
            String attributeName = gsonObj.get("name").getAsString();
            String attributeValue = "";
            if(!gsonObj.get("value_name").isJsonNull()){
                attributeValue = gsonObj.get("value_name").getAsString();
            }
            Attribute attribute = new Attribute(attributeName, attributeValue); 
            attributes.add(attribute);
        }
        return attributes;
    }

    public String getStringFromJson(String value, JsonObject gson){
        String result = "";
        if(!gson.get(value).isJsonNull()) {
            result = gson.get(value).getAsString();
        }
        return result;
    }

    public Double getDoubleFromJson(String value, JsonObject gson){
        Double result = 0.0;
        if(!gson.get(value).isJsonNull()) {
            result = gson.get(value).getAsDouble();
        }
        return result;
    }
}

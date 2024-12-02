package ar.edu.unq.apc.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ar.edu.unq.apc.model.Attribute;
import ar.edu.unq.apc.model.MercadoLibreProduct;

@Service
public class MercadoLibreProxyService {
    
    private RestTemplate restTemplate = new RestTemplate();

    //La busqueda en Mercado Libre solo permite 20 resultados
    private Integer limitOfResults = 20;

    private String token = "APP_USR-7663246217238920-120108-f4e2e4497eafd3f26c8ee50166c31544-1136666046";

    @Value("${integration.mercadolibre.api.url:NONE}")
    private String mercadoLibreApiURL;


    public MercadoLibreProduct getProductById(String id){
        String jsonResult = executeGetRequest(mercadoLibreApiURL + "/items?ids=" +
            id + "&attributes=id,price,category_id,title,pictures,condition,permalink");

        JsonParser parser = new JsonParser();
        MercadoLibreProduct product = new MercadoLibreProduct();
    
        // Obtain Array
        JsonArray gsonArr = parser.parse(jsonResult).getAsJsonArray();
    
        for (JsonElement obj : gsonArr) {
            // Object of array
           JsonObject gsonObj = obj.getAsJsonObject();
           JsonObject body = gsonObj.get("body").getAsJsonObject();
            product = deserializeProduct(body);
        }

        return product;
    }

    public List<MercadoLibreProduct> searchProductsByWords(String search){
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

    
    public List<MercadoLibreProduct> getProductsByIds( List<String> ids ){
        String idsToSearch = String.join(",", ids);
        String jsonResult = executeGetRequest(mercadoLibreApiURL + "/items?ids=" +
            idsToSearch + "&attributes=id,price,category_id,title,pictures,condition,permalink");

        JsonParser parser = new JsonParser();
        List<MercadoLibreProduct> products = new ArrayList<MercadoLibreProduct>();

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

    
    public MercadoLibreProduct deserializeProduct(JsonObject gsonObj){
        String title = gsonObj.get("title").getAsString();
        String condition = getStringFromJson("condition", gsonObj);
        String id = gsonObj.get("id").getAsString();
        String categoryId = gsonObj.get("category_id").getAsString();
        Double price = getDoubleFromJson("price", gsonObj);
        String link = gsonObj.get("permalink").getAsString();
            
        JsonArray gsonArrPictures = gsonObj.get("pictures").getAsJsonArray();
        List<String> pictures = extractUrlPictures(gsonArrPictures);

        return new MercadoLibreProduct(id, link, title, categoryId, price, condition, pictures);
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


    public List<Attribute> getProductAttributes(String productId){
        String jsonResult = executeGetRequest(mercadoLibreApiURL + "/items?ids=" +
            productId + "&attributes=attributes");

        JsonParser parser = new JsonParser();
        JsonArray gsonArr = parser.parse(jsonResult).getAsJsonArray();
        JsonObject body = new JsonObject();
    
        for (JsonElement obj : gsonArr) {
            // Object of array
           JsonObject gsonObj = obj.getAsJsonObject();
           body = gsonObj.get("body").getAsJsonObject();
        }
        return extractAttributes(body.get("attributes").getAsJsonArray());
    }


    public List<Attribute> extractAttributes(JsonArray gsonArr){
        List<Attribute> attributes = new ArrayList<Attribute>();
        for (JsonElement obj : gsonArr) {
            JsonObject gsonObj = obj.getAsJsonObject();
            String attributeName = getStringFromJson("name", gsonObj);
            String attributeValue = getStringFromJson("value_name", gsonObj);
            Attribute attribute = new Attribute(attributeName, attributeValue); 
            attributes.add(attribute);
        }
        return attributes;
    }


    private void setToken(String token){
        this.token = token;
    }

    public String executeGetRequest(String url){
        HttpEntity<Void> request;
        HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            return response.getBody(); 
        } catch (HttpClientErrorException e) {
            refreshMercadoLibreToken();

            headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            request = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            return response.getBody(); 
        }
    }

    private void refreshMercadoLibreToken(){
        HttpEntity<?> request;
        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("client_id", "7663246217238920");
        requestBody.add("client_secret", "XfOGdpCrbMJNj3ZP8qlW6W0MrBkn0Rre");
        requestBody.add("refresh_token", "TG-66ccd97c7d5f5800019cdfc2-1136666046");

        request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(mercadoLibreApiURL + "/oauth/token", HttpMethod.POST, request, String.class);

        JsonParser parser = new JsonParser();
        JsonObject gsonObj = parser.parse(response.getBody()).getAsJsonObject();
        String updatedToken = gsonObj.get("access_token").getAsString();

        setToken(updatedToken);
    }
}

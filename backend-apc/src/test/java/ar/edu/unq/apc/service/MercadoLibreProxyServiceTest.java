package ar.edu.unq.apc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ar.edu.unq.apc.model.MercadoLibreProduct;

public class MercadoLibreProxyServiceTest {

    
    private MercadoLibreProxyService mercadoLibreProxyService = new MercadoLibreProxyService();

    private JsonObject anyJsonObjectWithMoreValues;

    private JsonParser jsonParser;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    @Spy
    private MercadoLibreProxyService mlService = new MercadoLibreProxyService();

    @BeforeEach
    void init(){
        jsonParser = new JsonParser();
    }


    @Test
    void deserializeProductFromJsonTest() {

        String jsonProductWithMoreValues = "{" +
                                                "\"id\":\"MLA4521\"," +
                                                "\"permalink\":\"http://product.com\"," +
                                                "\"title\":\"Producto generico\"," +
                                                "\"category_id\":\"MLA56542\"," +
                                                "\"price\":1231.432," +
                                                "\"attributes\":[]," +
                                                "\"pictures\":[]," +
                                                "\"condition\":\"Nuevo\"," +
                                                "\"seller_id\":\"MLA454212\"" +
                                            "}";

        anyJsonObjectWithMoreValues =  jsonParser.parse(jsonProductWithMoreValues).getAsJsonObject();

        MercadoLibreProduct product = this.mercadoLibreProxyService.deserializeProduct(anyJsonObjectWithMoreValues);

        assertEquals(product.getId(), "MLA4521");
        assertEquals(product.getLink(), "http://product.com");
        assertEquals(product.getTitle(), "Producto generico");
        assertEquals(product.getCategoryId(), "MLA56542");
        assertEquals(product.getPrice(), 1231.432);
        assertEquals(product.getPictures(), List.of());
        assertEquals(product.getCondition(), "Nuevo");
    }


    @Test
    void whenAJsonObjectContainsAListOfPictureValuesThenItExtractsAListOfURLsTest() {

        String jsonPicturesWithMoreValues = "{" +
                                                "\"pictures\":[" +
                                                    "{" +
                                                        "\"secure_url\":\"www.url1.com\"," +
                                                        "\"title\":\"Titulo1\"," +
                                                        "\"size\":\"484x384\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"secure_url\":\"www.url2.com\"," +
                                                        "\"title\":\"Titulo2\"," +
                                                        "\"size\":\"584x484\"" +
                                                    "}" +
                                                "]" +
                                            "}";

    JsonObject gsonObj = jsonParser.parse(jsonPicturesWithMoreValues).getAsJsonObject();                                       

    JsonArray gsonArrPictures = gsonObj.get("pictures").getAsJsonArray();
    
    List<String> pictures = this.mercadoLibreProxyService.extractUrlPictures(gsonArrPictures);

    assertEquals(pictures, List.of("www.url1.com", "www.url2.com"));
    }


    @Test
    void whenAJsonObjectContainsAValueThenItReturnsAStringTest() {
        String jsonWithTitle = "{" +
                                    "\"title\":\"Titulo\"" +
                                "}";

        JsonObject jsonObjectWithTitle =  jsonParser.parse(jsonWithTitle).getAsJsonObject();

        String value = this.mercadoLibreProxyService.getStringFromJson("title", jsonObjectWithTitle);

        assertEquals(value, "Titulo");

    }

    @Test
    void whenAJsonObjectContainsANullValueThenItReturnsAEmptyStringTest() {

        String jsonWithTitle = "{" +
                                    "\"title\":null" +
                                "}";

        JsonObject jsonObjectWithTitle =  jsonParser.parse(jsonWithTitle).getAsJsonObject();

        String value = this.mercadoLibreProxyService.getStringFromJson("title", jsonObjectWithTitle);

        assertEquals(value, "");

    }

    @Test
    void whenAJsonObjectContainsAValueThenItReturnsADoubleTest() {
        String jsonWithPrice = "{" +
                                    "\"price\":234.65" +
                                "}";

        JsonObject jsonObjectWithPrice =  jsonParser.parse(jsonWithPrice).getAsJsonObject();

        Double value = this.mercadoLibreProxyService.getDoubleFromJson("price", jsonObjectWithPrice);

        assertEquals(value, 234.65);

    }

    @Test
    void whenAJsonObjectContainsANullValueThenItReturnsCeroTest() {
        String jsonWithPrice = "{" +
                                    "\"price\":null" +
                                "}";

        JsonObject jsonObjectWithPrice =  jsonParser.parse(jsonWithPrice).getAsJsonObject();

        Double value = this.mercadoLibreProxyService.getDoubleFromJson("price", jsonObjectWithPrice);

        assertEquals(value, 0.0);

    }
 
    
    @Test
    @SuppressWarnings("unchecked")
    @ExtendWith(MockitoExtension.class)
    void getProductByIdFromMercadoLibreSuccesfullyTest(){

        String jsonProduct = "[{" + 
                                    "\"code\":200, " +
                                    "\"body\":{" + 
                                                "\"pictures\":[],"+
                                                "\"price\":309000," +
                                                "\"permalink\":\"http://product.com\"," +
                                                "\"id\":\"MLA1391234903\","+
                                                "\"category_id\":\"MLA4344\","+
                                                "\"title\":\"Generic product\","+
                                                "\"condition\":\"new\""+
                                            "}"+
                                "}]";

        ResponseEntity<String> response = new ResponseEntity<String>(jsonProduct, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
        .thenReturn(response);

        MercadoLibreProduct product = this.mlService.getProductById("MLA4521");

        assertEquals("Generic product", product.getTitle());
        assertEquals("MLA1391234903", product.getId());
        assertEquals(309000.00, product.getPrice());
    }

}

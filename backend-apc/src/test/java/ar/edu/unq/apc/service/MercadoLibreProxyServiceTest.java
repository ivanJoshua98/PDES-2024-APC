package ar.edu.unq.apc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ar.edu.unq.apc.model.Attribute;
import ar.edu.unq.apc.model.Product;

public class MercadoLibreProxyServiceTest {

    
    private MercadoLibreProxyService mercadoLibreProxyService = new MercadoLibreProxyService();

    private JsonObject anyJsonObjectWithMoreValues;

    private JsonParser jsonParser;

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

        Product product = this.mercadoLibreProxyService.deserializeProduct(anyJsonObjectWithMoreValues);

        assertEquals(product.getId(), "MLA4521");
        assertEquals(product.getLink(), "http://product.com");
        assertEquals(product.getTitle(), "Producto generico");
        assertEquals(product.getCategoryId(), "MLA56542");
        assertEquals(product.getPrice(), 1231.432);
        assertEquals(product.getAttributes(), List.of());
        assertEquals(product.getPictures(), List.of());
        assertEquals(product.getCondition(), "Nuevo");
    }


    @Test
    void whenAJsonObjectContainsAListOfAttributeValuesThenItExtractsAListOfAttributeObjectTest() {

        String jsonAttributesWithMoreValues = "{" +
                                                "\"attributes\":[" +
                                                    "{" +
                                                        "\"name\":\"condition\"," +
                                                        "\"value_name\":\"new\"," +
                                                        "\"id\":\"MLA1325892\"," +
                                                        "\"category\":\"MLA568721\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"name\":\"color\"," +
                                                        "\"value_name\":\"blue\"," +
                                                        "\"id\":\"MLA34241\"," +
                                                        "\"category\":\"MLA454323\"" +
                                                    "}" +
                                                "]" +
                                            "}";

    JsonObject gsonObj = jsonParser.parse(jsonAttributesWithMoreValues).getAsJsonObject();                                       

    JsonArray gsonArrPictures = gsonObj.get("attributes").getAsJsonArray();
    
    List<Attribute> pictures = this.mercadoLibreProxyService.extractAttributes(gsonArrPictures);

    assertEquals(pictures.get(0).getName(), "condition");
    assertEquals(pictures.get(0).getValue(), "new");

    assertEquals(pictures.get(1).getName(), "color");
    assertEquals(pictures.get(1).getValue(), "blue");

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

}

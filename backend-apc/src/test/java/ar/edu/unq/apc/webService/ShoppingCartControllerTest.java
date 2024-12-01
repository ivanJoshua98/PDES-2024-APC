package ar.edu.unq.apc.webService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.annotation.DirtiesContext;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.ProductInCart;
import ar.edu.unq.apc.model.ShoppingCart;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.security.JWTProvider;
import ar.edu.unq.apc.service.ProductInCartService;
import ar.edu.unq.apc.service.ShoppingCartService;
import ar.edu.unq.apc.service.UserService;
import ar.edu.unq.apc.webService.dto.NewProductInCartDTO;
import ar.edu.unq.apc.webService.dto.NewShoppingCartDTO;
import ar.edu.unq.apc.webService.dto.ShoppingCartDTO;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)   
public class ShoppingCartControllerTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
	private JWTProvider jwtProvider;

    @Autowired 
    private UserService userService;

    @Autowired
    private ProductInCartService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    private HttpHeaders headersWithTokenUser;

    private HttpHeaders headersWithTokenAdmin;

    private HttpHeaders headersWithoutToken;

    private UserModel anyUser;

    private UserModel otherUser;

    private ShoppingCart anyCart;

    private ProductInCart anyProduct;


    @BeforeEach
	public void init() {
    	String tokenUser = this.jwtProvider.generateToken(
	        new UsernamePasswordAuthenticationToken("userBuyer@mail.com", "Credential.")
        );	 
        String tokenAdmin = this.jwtProvider.generateToken(
	        new UsernamePasswordAuthenticationToken("userAdmin@mail.com", "Credential.")
        );	     	
	    this.headersWithTokenUser = new HttpHeaders();
		headersWithTokenUser.set("Authorization", "Bearer " + tokenUser);

        this.headersWithTokenAdmin = new HttpHeaders();
		headersWithTokenAdmin.set("Authorization", "Bearer " + tokenAdmin);

        this.headersWithoutToken = new HttpHeaders();
        this.anyUser = userService.getUserByEmail("userBuyer@mail.com");
        this.otherUser = userService.getUserByEmail("userAdmin@mail.com");
        this.anyCart = shoppingCartService.getShoppingCartByBuyer(anyUser);
        this.anyProduct = productService.getProductByMercadoLibreId("MLA1456046051");
	}


    @Test
    @DirtiesContext
    void createNewShoppingCartSuccesfullyTest() { 
        NewProductInCartDTO newProduct = new NewProductInCartDTO("MLAid", 1, "picture", "link", "title", "categorydId", 100.00, "new");
        NewShoppingCartDTO newCart = new NewShoppingCartDTO(100.00, List.of(newProduct), otherUser.getId());

        HttpEntity<NewShoppingCartDTO> postRequest = new HttpEntity<NewShoppingCartDTO>(newCart, headersWithTokenUser);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.postForEntity(HTTP_LOCALHOST + port + "/apc/shopping-cart/new-shopping-cart", postRequest, ShoppingCartDTO.class);

        ShoppingCartDTO createdCart = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(createdCart.getTotalAmountPurchase(), 100.0);
        assertEquals(CartState.INPROGRESS, createdCart.getCartState());
    }

    @Test
    void getShoppingCartByIdWithoutTokenResponds401UnauthorizedTest() {
        HttpEntity<Void> getRequest = new HttpEntity<>(headersWithoutToken);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/admin/shopping-cart/ab5be3d6-4e78-4aaf-bf7f-5e1db665e13b",
            HttpMethod.GET,
            getRequest,
            ShoppingCartDTO.class);

        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    void whenAUserGetsAShoppingCartByIdWithoutAdminRoleThenItsResponds403ForbiddenTest() {
        HttpEntity<Void> getRequest = new HttpEntity<>(headersWithTokenUser);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/admin/shopping-cart/ab5be3d6-4e78-4aaf-bf7f-5e1db665e13b",
            HttpMethod.GET,
            getRequest,
            ShoppingCartDTO.class);

        assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);
    }


    @Test
    void getShoppingCartInProgressToLoggedInUserSuccesfullyTest() {
        HttpEntity<Void> getRequest = new HttpEntity<>(headersWithTokenUser);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shopping-cart/inprogress",
            HttpMethod.GET,
            getRequest,
            ShoppingCartDTO.class);

        ShoppingCartDTO cart = result.getBody();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(anyUser.getId(), cart.getBuyerId());
    }

    @Test
    void getShoppingCartInProgressUnsuccesfullyTest() {
        HttpEntity<Void> getRequest = new HttpEntity<>(headersWithTokenAdmin);
        ResponseEntity<String> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shopping-cart/inprogress",
            HttpMethod.GET,
            getRequest,
            String.class);

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals("Cart in progress not found", result.getBody());
    }


    @Test
    @DirtiesContext
    void addProductToTheShoppingCartInProgressSuccesfullyTest() {
        Double totalOfCart = anyCart.getTotalAmountPurchase();
        NewProductInCartDTO newProduct = new NewProductInCartDTO("MLAid", 1, "picture", "link", "title", "categorydId", 100.00, "new");

        HttpEntity<NewProductInCartDTO> putRequest = new HttpEntity<NewProductInCartDTO>(newProduct, headersWithTokenUser);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shopping-cart/inprogress/add-product",
            HttpMethod.PUT,
            putRequest,
            ShoppingCartDTO.class);

        ShoppingCartDTO updatedCart = result.getBody();

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(2, updatedCart.getProductsInCart().size());
        assertEquals(totalOfCart + newProduct.getPrice(), updatedCart.getTotalAmountPurchase());
        assertEquals(CartState.INPROGRESS, updatedCart.getCartState());
    }

    @Test
    @DirtiesContext
    void addProductOneTimeToTheShoppingCartInProgressSuccesfullyTest() {
        Double totalOfCart = anyCart.getTotalAmountPurchase();
        Integer amountOfProduct = anyProduct.getAmount();

        HttpEntity<ShoppingCartDTO> putRequest = new HttpEntity<ShoppingCartDTO>(headersWithTokenUser);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shopping-cart/inprogress/add-product-one-time/" + anyProduct.getId(),
            HttpMethod.PUT,
            putRequest,
            ShoppingCartDTO.class);

        ShoppingCartDTO updatedCart = result.getBody();

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(amountOfProduct + 1, updatedCart.getProductsInCart().get(0).getAmount());
        assertEquals(totalOfCart + anyProduct.getPrice(), updatedCart.getTotalAmountPurchase());
        assertEquals(CartState.INPROGRESS, updatedCart.getCartState());
    }

    @Test
    @DirtiesContext
    void removeProductToTheShoppingCartInProgressSuccesfully() {
        Integer amountOfProducts = anyCart.getCart().size();
        Double totalOfCart = anyCart.getTotalAmountPurchase();

        HttpEntity<ShoppingCartDTO> putRequest = new HttpEntity<ShoppingCartDTO>(headersWithTokenUser);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shopping-cart/inprogress/remove-product/" + anyProduct.getId(),
            HttpMethod.PUT,
            putRequest,
            ShoppingCartDTO.class);

        ShoppingCartDTO updatedCart = result.getBody();

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(amountOfProducts - 1, updatedCart.getProductsInCart().size());
        assertEquals(totalOfCart - anyProduct.getPrice(), updatedCart.getTotalAmountPurchase());
        assertEquals(CartState.INPROGRESS, updatedCart.getCartState());
    }

    @Test
    @DirtiesContext
    void whenRemovesAProductToTheShoppingCartWithJustOneProductThenItDeletesTheShoppingCart() {
        UUID cartId = anyCart.getId();

        HttpEntity<ShoppingCartDTO> putRequest = new HttpEntity<ShoppingCartDTO>(headersWithTokenUser);
        
        restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shopping-cart/inprogress/remove-product/" + anyProduct.getId(),
            HttpMethod.PUT,
            putRequest,
            ShoppingCartDTO.class);
        
        HttpException exception = assertThrows(HttpException.class, () -> shoppingCartService.getShoppingCartById(cartId));

        assertEquals("Cart not found by id: " + cartId, exception.getMessage());
    }

    @Test
    @DirtiesContext
    void finishPurchaseWithShoppingCartInProgressSuccesfullyTest() {
        ShoppingCart cart = shoppingCartService.getShoppingCartByBuyer(anyUser);
        CartState stateBeforeFinish = cart.getCartState();

        HttpEntity<ShoppingCartDTO> putRequest = new HttpEntity<ShoppingCartDTO>(headersWithTokenUser);
        
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shopping-cart/inprogress/finish-purchase",
            HttpMethod.PUT,
            putRequest,
            ShoppingCartDTO.class);

        ShoppingCartDTO soldShoppingCart = result.getBody();

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotEquals(stateBeforeFinish, soldShoppingCart.getCartState());
        assertEquals(CartState.SOLD, soldShoppingCart.getCartState());
    }

    @Test
    @DirtiesContext
    void deleteShoppingCartWithAdminRoleSuccesfullyTest() {
        UUID cartId = anyCart.getId();

        HttpEntity<ShoppingCartDTO> deleteRequest = new HttpEntity<ShoppingCartDTO>(headersWithTokenAdmin);
        
        ResponseEntity<String> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/admin/shopping-cart/" + cartId + "/delete-shopping-cart",
            HttpMethod.DELETE,
            deleteRequest,
            String.class);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        HttpException exception = assertThrows(HttpException.class, () -> shoppingCartService.getShoppingCartById(cartId));
        assertEquals("Cart not found by id: " + cartId, exception.getMessage());
    }

    @Test
    @DirtiesContext
    void deleteShoppingCartInProgressSuccesfullyTest() {
        UUID cartId = anyCart.getId();

        HttpEntity<ShoppingCartDTO> deleteRequest = new HttpEntity<ShoppingCartDTO>(headersWithTokenUser);
        
        ResponseEntity<String> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shopping-cart/inprogress/delete-shopping-cart",
            HttpMethod.DELETE,
            deleteRequest,
            String.class);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        HttpException exception = assertThrows(HttpException.class, () -> shoppingCartService.getShoppingCartById(cartId));
        assertEquals("Cart not found by id: " + cartId, exception.getMessage());
    }

    @Test
    void getAllShoppingCartsByUserWithAdminRoleSuccesfullyTest() {
        HttpEntity<Void> getRequest = new HttpEntity<>(headersWithTokenAdmin);
        ResponseEntity<ShoppingCartDTO[]> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/admin/shopping-cart/all-purchases/"+ anyUser.getId(),
            HttpMethod.GET,
            getRequest,
            ShoppingCartDTO[].class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}

package ar.edu.unq.apc.webService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    private String token;

    private HttpHeaders headersWithToken;

    private HttpHeaders headersWithoutToken;

    private UserModel anyUser;

    private UserModel otherUser;

    private ShoppingCart anyCart;

    private ProductInCart anyProduct;


    @BeforeEach
	public void init() {
    	this.token = this.jwtProvider.generateToken(
	        new UsernamePasswordAuthenticationToken("userBuyer@mail.com", "credential")
        );	    	
	    this.headersWithToken = new HttpHeaders();
		headersWithToken.set("Authorization", "Bearer " + token);

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

        HttpEntity<NewShoppingCartDTO> postRequest = new HttpEntity<NewShoppingCartDTO>(newCart, headersWithToken);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.postForEntity(HTTP_LOCALHOST + port + "/apc/shoppingCart/newShoppingCart", postRequest, ShoppingCartDTO.class);

        ShoppingCartDTO createdCart = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(createdCart.getTotalAmountPurchase(), 100.0);
        assertEquals(createdCart.getCartState(), CartState.INPROGRESS);
    }

    @Test
    void getShoppingCartByIdWithoutTokenResponds401UnauthorizedTest() {
        HttpEntity<Void> getRequest = new HttpEntity<>(headersWithoutToken);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shoppingCart/ab5be3d6-4e78-4aaf-bf7f-5e1db665e13b",
            HttpMethod.GET,
            getRequest,
            ShoppingCartDTO.class);

        assertEquals(result.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    void getShoppingCartInProgressByUserIdSuccesfullyTest() {
        HttpEntity<Void> getRequest = new HttpEntity<>(headersWithToken);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shoppingCart/inprogress/"+ anyUser.getId(),
            HttpMethod.GET,
            getRequest,
            ShoppingCartDTO.class);

        ShoppingCartDTO cart = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(cart.getBuyerId(), anyUser.getId());
    }

    @Test
    void getShoppingCartInProgressByUserIdUnsuccesfullyTest() {
        HttpEntity<Void> getRequest = new HttpEntity<>(headersWithToken);
        ResponseEntity<String> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shoppingCart/inprogress/"+ otherUser.getId(),
            HttpMethod.GET,
            getRequest,
            String.class);

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(result.getBody(), "Cart in progress not found");
    }

    @Test
    void getAllPurchasesSuccesfullyTest() {
        HttpEntity<Void> getRequest = new HttpEntity<>(headersWithToken);
        ResponseEntity<ShoppingCartDTO[]> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shoppingCart/allShoppingCarts",
            HttpMethod.GET,
            getRequest,
            ShoppingCartDTO[].class);

        ShoppingCartDTO[] carts = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertNotNull(carts[0]);
    }

    @Test
    @DirtiesContext
    void addProductToTheShoppingCartSuccesfullyTest() {
        Double totalOfCart = anyCart.getTotalAmountPurchase();
        NewProductInCartDTO newProduct = new NewProductInCartDTO("MLAid", 1, "picture", "link", "title", "categorydId", 100.00, "new");

        HttpEntity<NewProductInCartDTO> putRequest = new HttpEntity<NewProductInCartDTO>(newProduct, headersWithToken);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shoppingCart/" + anyCart.getId() + "/addProduct",
            HttpMethod.PUT,
            putRequest,
            ShoppingCartDTO.class);

        ShoppingCartDTO updatedCart = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(updatedCart.getProductsInCart().size(), 2);
        assertEquals(updatedCart.getTotalAmountPurchase(), totalOfCart + newProduct.getPrice());
        assertEquals(updatedCart.getCartState(), CartState.INPROGRESS);
    }

    @Test
    @DirtiesContext
    void addProductOneTimeToTheShoppingCartSuccesfullyTest() {
        Double totalOfCart = anyCart.getTotalAmountPurchase();
        Integer amountOfProduct = anyProduct.getAmount();

        HttpEntity<ShoppingCartDTO> putRequest = new HttpEntity<ShoppingCartDTO>(headersWithToken);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shoppingCart/" + anyCart.getId() + "/addProductOneTime/" + anyProduct.getId(),
            HttpMethod.PUT,
            putRequest,
            ShoppingCartDTO.class);

        ShoppingCartDTO updatedCart = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(updatedCart.getProductsInCart().get(0).getAmount(), amountOfProduct + 1);
        assertEquals(updatedCart.getTotalAmountPurchase(), totalOfCart + anyProduct.getPrice());
        assertEquals(updatedCart.getCartState(), CartState.INPROGRESS);
    }

    @Test
    @DirtiesContext
    void removeProductToTheShoppingCartSuccesfully() {
        Integer amountOfProducts = anyCart.getCart().size();
        Double totalOfCart = anyCart.getTotalAmountPurchase();

        HttpEntity<ShoppingCartDTO> putRequest = new HttpEntity<ShoppingCartDTO>(headersWithToken);
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shoppingCart/" + anyCart.getId() + "/removeProduct/" + anyProduct.getId(),
            HttpMethod.PUT,
            putRequest,
            ShoppingCartDTO.class);

        ShoppingCartDTO updatedCart = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(updatedCart.getProductsInCart().size(), amountOfProducts - 1);
        assertEquals(updatedCart.getTotalAmountPurchase(), totalOfCart - anyProduct.getPrice());
        assertEquals(updatedCart.getCartState(), CartState.INPROGRESS);
    }

    @Test
    @DirtiesContext
    void whenRemovesAProductToTheShoppingCartWithJustOneProductThenItDeletesTheShoppingCart() {
        UUID cartId = anyCart.getId();

        HttpEntity<ShoppingCartDTO> putRequest = new HttpEntity<ShoppingCartDTO>(headersWithToken);
        
        restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shoppingCart/" + anyCart.getId() + "/removeProduct/" + anyProduct.getId(),
            HttpMethod.PUT,
            putRequest,
            ShoppingCartDTO.class);
        
        HttpException exception = assertThrows(HttpException.class, () -> shoppingCartService.getShoppingCartById(cartId));

        assertEquals(exception.getMessage(), "Cart not found by id: " + cartId);
    }

    @Test
    @DirtiesContext
    void finishPurchaseSuccesfullyTest() {
        ShoppingCart cart = shoppingCartService.getShoppingCartByBuyer(anyUser);
        CartState stateBeforeFinish = cart.getCartState();

        HttpEntity<ShoppingCartDTO> putRequest = new HttpEntity<ShoppingCartDTO>(headersWithToken);
        
        ResponseEntity<ShoppingCartDTO> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shoppingCart/" + cart.getId() + "/finishPurchase",
            HttpMethod.PUT,
            putRequest,
            ShoppingCartDTO.class);

        ShoppingCartDTO soldShoppingCart = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertNotEquals(soldShoppingCart.getCartState(), stateBeforeFinish);
        assertEquals(soldShoppingCart.getCartState(), CartState.SOLD);
    }

    @Test
    @DirtiesContext
    void deleteShoppingCartSuccesfullyTest() {
        UUID cartId = anyCart.getId();

        HttpEntity<ShoppingCartDTO> deleteRequest = new HttpEntity<ShoppingCartDTO>(headersWithToken);
        
        ResponseEntity<String> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shoppingCart/" + cartId + "/deleteShoppingCart",
            HttpMethod.DELETE,
            deleteRequest,
            String.class);

        assertEquals(result.getStatusCode(), HttpStatus.NO_CONTENT);
        HttpException exception = assertThrows(HttpException.class, () -> shoppingCartService.getShoppingCartById(cartId));
        assertEquals(exception.getMessage(), "Cart not found by id: " + cartId);
    }

    @Test
    void getAllFinishedShoppingCartsOfAUserSuccesfullyTest() {
        HttpEntity<Void> getRequest = new HttpEntity<>(headersWithToken);
        ResponseEntity<ShoppingCartDTO[]> result = restTemplate.exchange(HTTP_LOCALHOST + port + "/apc/shoppingCart/allPurchases/"+ anyUser.getId(),
            HttpMethod.GET,
            getRequest,
            ShoppingCartDTO[].class);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

}

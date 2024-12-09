package ar.edu.unq.apc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.ProductInCart;
import ar.edu.unq.apc.model.Role;
import ar.edu.unq.apc.model.ShoppingCart;
import ar.edu.unq.apc.model.UserModel;
import jakarta.annotation.PostConstruct;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

@Service
@Transactional
public class InitServiceInMemory {
    
    @Value("${spring.datasource.driverClassName}")
	private String className;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductInCartService productService;

    protected final Log logger = LogFactory.getLog(getClass());

    @PostConstruct
	public void initialize() {
        if (className.equals("org.h2.Driver")) {
			logger.warn("Init Data Using H2 DB");
			fireInitialDataToH2();
		} else {
            logger.warn("Init Data Using Postgres DB");
            fireInitialDataToPostgres();
        }
		
	}

  
    private void fireInitialDataToH2() {
        createAndSaveRolesToH2();
        createAndSaveUsersToH2();
        createAndSaveShoppingCartsToH2();
    }

    private void createAndSaveRolesToH2(){
        Role role1 = new Role("USER");
        Role role2 = new Role("ADMIN");
        roleService.saveRole(role1);
        roleService.saveRole(role2);
    }

    private void createAndSaveUsersToH2(){
        Role userRole = this.roleService.getByName("USER");
        Role adminRole = this.roleService.getByName("ADMIN");

        UserModel user = new UserModel("userBuyer", "userBuyer@mail.com", "Credential.");
        user.addRole(userRole);
        
        UserModel user1 = new UserModel("userAdmin", "userAdmin@mail.com", "Credential.");
        user1.addRole(userRole);
        user1.addRole(adminRole);

        this.userService.saveUser(user);
        this.userService.saveUser(user1);
    }

    private void createAndSaveShoppingCartsToH2(){
        UserModel user = this.userService.getUserByEmail("userBuyer@mail.com");
        ProductInCart smartphone = smartphone(1);
        ShoppingCart newCart = new ShoppingCart(List.of(smartphone), 447814.0, user);

        this.productService.saveProduct(smartphone);
        this.shoppingCartService.saveShoppingCart(newCart);
    }

//---------------------------------------------------INITIAL DATA TO POSTGRES--------------------------------------------------------------//

    private void fireInitialDataToPostgres() {
        createAndSaveRolesToPostgres();
        createAndSaveUsersToPostgres();
        createAndSaveShoppingCartsToPostgres();
    }

    private void createAndSaveRolesToPostgres(){
        Role role1 = new Role("USER");
        Role role2 = new Role("ADMIN");
        saveRoleToPostgres(role1);
        saveRoleToPostgres(role2);
    }

    private void saveRoleToPostgres(Role role){
        if(!this.roleService.existsByName(role.getName())){
            this.roleService.saveRole(role);
        }
    }

    private void createAndSaveUsersToPostgres(){
        Role userRole = this.roleService.getByName("USER");
        Role adminRole = this.roleService.getByName("ADMIN");

        UserModel buyer1 = new UserModel("userBuyer1", "userBuyer1@mail.com", "Credential.");
        buyer1.addRole(userRole);

        UserModel buyer2 = new UserModel("userBuyer2", "userBuyer2@mail.com", "Credential.");
        buyer2.addRole(userRole);

        UserModel buyer3 = new UserModel("userBuyer3", "userBuyer3@mail.com", "Credential.");
        buyer3.addRole(userRole);

        UserModel buyer4 = new UserModel("userBuyer4", "userBuyer4@mail.com", "Credential.");
        buyer4.addRole(userRole);

        UserModel buyer5 = new UserModel("userBuyer5", "userBuyer5@mail.com", "Credential.");
        buyer5.addRole(userRole);

        UserModel buyer6 = new UserModel("userBuyer6", "userBuyer6@mail.com", "Credential.");
        buyer6.addRole(userRole);
        
        UserModel admin = new UserModel("userAdmin", "userAdmin@mail.com", "Credential.");
        admin.addRole(userRole);
        admin.addRole(adminRole);

        if(!this.userService.existsByEmail("userBuyer6@mail.com")){

            admin = userService.saveUser(admin);
            buyer1 = userService.saveUser(buyer1);
            buyer2 = userService.saveUser(buyer2);
            buyer3 = userService.saveUser(buyer3);
            buyer4 = userService.saveUser(buyer4);
            buyer5 = userService.saveUser(buyer5);
            buyer6 = userService.saveUser(buyer6);

            buyer1 = userService.updateMoreFavorites("MLA1452204905", buyer1);
            buyer1 = userService.updateMoreFavorites("MLA1853954300", buyer1);
            buyer1 = userService.updateMoreFavorites("MLA1391234903", buyer1);
            buyer1 = userService.updateMoreFavorites("MLA1891257852", buyer1);
            buyer1 = userService.updateMoreFavorites("MLA1462782165", buyer1);
            buyer1 = userService.updateMoreFavorites("MLA1456046051", buyer1);

            buyer2 = userService.updateMoreFavorites("MLA1452204905", buyer2);
            buyer2 = userService.updateMoreFavorites("MLA1853954300", buyer2);
            buyer2 = userService.updateMoreFavorites("MLA1391234903", buyer2);
            buyer2 = userService.updateMoreFavorites("MLA1891257852", buyer2);
            buyer2 = userService.updateMoreFavorites("MLA1462782165", buyer2);

            buyer3 = userService.updateMoreFavorites("MLA1452204905", buyer3);
            buyer3 = userService.updateMoreFavorites("MLA1853954300", buyer3);
            buyer3 = userService.updateMoreFavorites("MLA1391234903", buyer3);
            buyer3 = userService.updateMoreFavorites("MLA1891257852", buyer3);

            buyer4 = userService.updateMoreFavorites("MLA1452204905", buyer4);
            buyer4 = userService.updateMoreFavorites("MLA1853954300", buyer4);
            buyer4 = userService.updateMoreFavorites("MLA1391234903", buyer4);

            buyer5 = userService.updateMoreFavorites("MLA1452204905", buyer5);
            buyer5 = userService.updateMoreFavorites("MLA1853954300", buyer5);
            
            buyer6 = userService.updateMoreFavorites("MLA1452204905", buyer6);
        }
    }

    private void createAndSaveShoppingCartsToPostgres(){
        UserModel buyer1 = this.userService.getUserByEmail("userBuyer1@mail.com");
        UserModel buyer2 = this.userService.getUserByEmail("userBuyer2@mail.com");
        UserModel buyer3 = this.userService.getUserByEmail("userBuyer3@mail.com");
        UserModel buyer4 = this.userService.getUserByEmail("userBuyer4@mail.com");
        UserModel buyer5 = this.userService.getUserByEmail("userBuyer5@mail.com");
        UserModel buyer6 = this.userService.getUserByEmail("userBuyer6@mail.com");
        try {
            this.shoppingCartService.getShoppingCartInProgressByBuyer(buyer6);
        } catch (HttpException e) {
            //Si no se obtiene el carrito en proceso del comprador 6, es porque no se creo nada, por lo que puedo inicializar datos en db.
            
            //Carritos del comprador 1
            ShoppingCart cartABuyer1 = new ShoppingCart();
            cartABuyer1.addProduct(notebook(2));
            cartABuyer1.setBuyer(buyer1);
            cartABuyer1.setCartState(CartState.SOLD);

            ShoppingCart cartBBuyer1 = new ShoppingCart();
            cartBBuyer1.addProduct(smartphone(2));
            cartBBuyer1.setBuyer(buyer1);
            cartBBuyer1.setCartState(CartState.SOLD);

            ShoppingCart cartCBuyer1 = new ShoppingCart();
            cartCBuyer1.addProduct(fridge(1));
            cartCBuyer1.setBuyer(buyer1);
            cartCBuyer1.setCartState(CartState.SOLD);
            
            ShoppingCart cartDBuyer1 = new ShoppingCart();
            cartDBuyer1.addProduct(kitchen(1));
            cartDBuyer1.setBuyer(buyer1);
            cartDBuyer1.setCartState(CartState.SOLD);

            ShoppingCart cartEBuyer1 = new ShoppingCart();
            cartEBuyer1.addProduct(computer(1));
            cartEBuyer1.setBuyer(buyer1);
            cartEBuyer1.setCartState(CartState.SOLD);

            ShoppingCart cartFBuyer1 = new ShoppingCart();
            cartFBuyer1.addProduct(headphones(1));
            cartFBuyer1.setBuyer(buyer1);
            cartFBuyer1.setCartState(CartState.SOLD);
            
            
           //Carritos del comprador 2           
            ShoppingCart cartABuyer2 = new ShoppingCart();
            cartABuyer2.addProduct(notebook(1));
            cartABuyer2.setBuyer(buyer2);
            cartABuyer2.setCartState(CartState.SOLD);

            ShoppingCart cartBBuyer2 = new ShoppingCart();
            cartBBuyer2.addProduct(smartphone(1));
            cartBBuyer2.setBuyer(buyer2);
            cartBBuyer2.setCartState(CartState.SOLD);

            ShoppingCart cartCBuyer2 = new ShoppingCart();
            cartCBuyer2.addProduct(fridge(1));
            cartCBuyer2.setBuyer(buyer2);
            cartCBuyer2.setCartState(CartState.SOLD);

            ShoppingCart cartDBuyer2 = new ShoppingCart();
            cartDBuyer2.addProduct(kitchen(1));
            cartDBuyer2.setBuyer(buyer2);
            cartDBuyer2.setCartState(CartState.SOLD);
            
            ShoppingCart cartEBuyer2 = new ShoppingCart();
            cartEBuyer2.addProduct(computer(1));
            cartEBuyer2.setBuyer(buyer2);
            cartEBuyer2.setCartState(CartState.SOLD);
            
            
            //Carritos del comprador 3
            ShoppingCart cartABuyer3 = new ShoppingCart();
            cartABuyer3.addProduct(notebook(1));
            cartABuyer3.setBuyer(buyer3);
            cartABuyer3.setCartState(CartState.SOLD);

            ShoppingCart cartBBuyer3 = new ShoppingCart();
            cartBBuyer3.addProduct(smartphone(1));
            cartBBuyer3.setBuyer(buyer3);
            cartBBuyer3.setCartState(CartState.SOLD);

            ShoppingCart cartCBuyer3 = new ShoppingCart();
            cartCBuyer3.addProduct(fridge(1));
            cartCBuyer3.setBuyer(buyer3);
            cartCBuyer3.setCartState(CartState.SOLD);
            
            ShoppingCart cartDBuyer3 = new ShoppingCart();
            cartDBuyer3.addProduct(kitchen(1));
            cartDBuyer3.setBuyer(buyer3);
            cartDBuyer3.setCartState(CartState.SOLD);
            

            //Carritos del comprador 4
            ShoppingCart cartABuyer4 = new ShoppingCart();
            cartABuyer4.addProduct(notebook(1));
            cartABuyer4.setBuyer(buyer4);
            cartABuyer4.setCartState(CartState.SOLD);

            ShoppingCart cartBBuyer4 = new ShoppingCart();
            cartBBuyer4.addProduct(smartphone(1));
            cartBBuyer4.setBuyer(buyer4);
            cartBBuyer4.setCartState(CartState.SOLD);
            
            ShoppingCart cartCBuyer4 = new ShoppingCart();
            cartCBuyer4.addProduct(fridge(1));
            cartCBuyer4.setBuyer(buyer4);
            cartCBuyer4.setCartState(CartState.SOLD);

            
            //Carritos del comprador 5
            ShoppingCart cartABuyer5 = new ShoppingCart();
            cartABuyer5.addProduct(notebook(1));
            cartABuyer5.setBuyer(buyer5);
            cartABuyer5.setCartState(CartState.SOLD);

            ShoppingCart cartBBuyer5 = new ShoppingCart();
            cartBBuyer5.addProduct(smartphone(1));
            cartBBuyer5.setBuyer(buyer5);
            cartBBuyer5.setCartState(CartState.SOLD);
            
            
            //Carritos del comprador 6
            ShoppingCart cartABuyer6 = new ShoppingCart();
            cartABuyer6.addProduct(notebook(1));
            cartABuyer6.addProduct(headphones(15));
            cartABuyer6.setBuyer(buyer6);
            cartABuyer6.setCartState(CartState.SOLD);

            ShoppingCart cartInprogressBuyer6 = new ShoppingCart();
            cartInprogressBuyer6.addProduct(smartphone(1));
            cartInprogressBuyer6.setBuyer(buyer6);
            cartInprogressBuyer6.setCartState(CartState.INPROGRESS);

            saveProductsInCart(cartABuyer1);
            saveProductsInCart(cartBBuyer1);
            saveProductsInCart(cartCBuyer1);
            saveProductsInCart(cartDBuyer1);
            saveProductsInCart(cartEBuyer1);
            saveProductsInCart(cartFBuyer1);
            this.shoppingCartService.updateShoppingCart(cartABuyer1);
            this.shoppingCartService.updateShoppingCart(cartBBuyer1);
            this.shoppingCartService.updateShoppingCart(cartCBuyer1);
            this.shoppingCartService.updateShoppingCart(cartDBuyer1);
            this.shoppingCartService.updateShoppingCart(cartEBuyer1);
            this.shoppingCartService.updateShoppingCart(cartFBuyer1);


            saveProductsInCart(cartABuyer2);
            saveProductsInCart(cartBBuyer2);
            saveProductsInCart(cartCBuyer2);
            saveProductsInCart(cartDBuyer2);
            saveProductsInCart(cartEBuyer2);
            this.shoppingCartService.updateShoppingCart(cartABuyer2);
            this.shoppingCartService.updateShoppingCart(cartBBuyer2);
            this.shoppingCartService.updateShoppingCart(cartCBuyer2);
            this.shoppingCartService.updateShoppingCart(cartDBuyer2);
            this.shoppingCartService.updateShoppingCart(cartEBuyer2);


            saveProductsInCart(cartABuyer3);
            saveProductsInCart(cartBBuyer3);
            saveProductsInCart(cartCBuyer3);
            saveProductsInCart(cartDBuyer3);
            this.shoppingCartService.updateShoppingCart(cartABuyer3);
            this.shoppingCartService.updateShoppingCart(cartBBuyer3);
            this.shoppingCartService.updateShoppingCart(cartCBuyer3);
            this.shoppingCartService.updateShoppingCart(cartDBuyer3);


            saveProductsInCart(cartABuyer4);
            saveProductsInCart(cartBBuyer4);
            saveProductsInCart(cartCBuyer4);
            this.shoppingCartService.updateShoppingCart(cartABuyer4);
            this.shoppingCartService.updateShoppingCart(cartBBuyer4);
            this.shoppingCartService.updateShoppingCart(cartCBuyer4);

            
            saveProductsInCart(cartABuyer5);
            saveProductsInCart(cartBBuyer5);
            this.shoppingCartService.updateShoppingCart(cartABuyer5);
            this.shoppingCartService.updateShoppingCart(cartBBuyer5);

            
            saveProductsInCart(cartABuyer6);
            saveProductsInCart(cartInprogressBuyer6);
            this.shoppingCartService.updateShoppingCart(cartABuyer6);
            this.shoppingCartService.saveShoppingCart(cartInprogressBuyer6);
        }
    }


    private void saveProductsInCart(ShoppingCart shoppingCart){
        for(ProductInCart product : shoppingCart.getCart()){
            this.productService.saveProduct(product);
        }
    }

    private ProductInCart notebook(Integer amount){
        return new ProductInCart("https://articulo.mercadolibre.com.ar/MLA-1462782165-notebook-philco-n14p4020-141-pulgadas-intel-celeron-4gb-128gb-ssd-windows-11-_JM", 
                                "Notebook Philco N14p4020 14.1 Pulgadas Intel Celeron 4gb 128gb Ssd Windows 11", 
                                "MLA1652",
                                570999.00, 
                                "new", 
                                "MLA1462782165", 
                                amount, 
                                "https://http2.mlstatic.com/D_653760-MLA80066799098_102024-O.jpg");
    }

    private ProductInCart smartphone(Integer amount){
        return new ProductInCart("https://articulo.mercadolibre.com.ar/MLA-1456046051-xiaomi-redmi-note-13-pro-4g-dual-sim-256-gb-purpura-8-gb-ram-_JM",
                                "Xiaomi Redmi Note 13 Pro 4g Dual Sim 256 Gb  Púrpura 8 Gb Ram",
                                "MLA1055", 
                                447814.0, 
                                "new", 
                                "MLA1456046051",
                                1,
                                "https://http2.mlstatic.com/D_658212-MLA75588485012_042024-O.jpg");
    }

    private ProductInCart fridge(Integer amount){
        return new ProductInCart("https://articulo.mercadolibre.com.ar/MLA-1891257852-heladera-whirlpool-no-frost-340-lts-evox-color-inoxidable-_JM",
                                "Heladera Whirlpool No Frost 340 Lts - Evox Color Inoxidable", 
                                "MLA398582", 
                                849999.00, 
                                "new", 
                                "MLA1891257852", 
                                amount, 
                                "https://http2.mlstatic.com/D_728649-MLU78123887221_082024-O.jpg");
    }

    private ProductInCart kitchen(Integer amount){
        return new ProductInCart("https://articulo.mercadolibre.com.ar/MLA-1391234903-cocina-escorial-candor-s2-black-color-negro-con-puerta-con-visor-gas-envasado-_JM", 
                                "Cocina Escorial Candor S2 Black Color Negro Con Puerta Con Visor Gas Envasado", 
                                "MLA4344", 
                                309000.00, 
                                "new", 
                                "MLA1391234903", 
                                amount, 
                                "https://http2.mlstatic.com/D_757347-MLU77304841578_072024-O.jpg");
    }

    private ProductInCart computer(Integer amount){
        return new ProductInCart("https://articulo.mercadolibre.com.ar/MLA-1853954300-computadora-cpu-core-i7-16gb-ssd-480gb-wifi-w10-office-_JM", 
                                "Computadora Cpu Core I7 16gb Ssd 480gb Wifi W10 Office", 
                                "MLA1649", 
                                390000.00, 
                                "new", 
                                "MLA1853954300", 
                                amount, 
                                "https://http2.mlstatic.com/D_797419-MLA80476219168_112024-O.jpg");
    }

    private ProductInCart headphones(Integer amount){
        return new ProductInCart("https://articulo.mercadolibre.com.ar/MLA-1452204905-auriculares-xiaomi-redmi-buds-6-active-bluetooth-54-rosa-_JM", 
                                "Auriculares Xiaomi Redmi Buds 6 Active Bluetooth 5.4 Rosa", 
                                "MLA3697", 
                                41999.00, 
                                "new", 
                                "MLA1452204905", 
                                amount, 
                                "https://http2.mlstatic.com/D_820352-MLU78262924446_082024-O.jpg");
    }

}

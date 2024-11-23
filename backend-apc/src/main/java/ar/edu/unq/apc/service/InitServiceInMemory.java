package ar.edu.unq.apc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private void fireInitialDataToPostgres() {
        createAndSaveRolesToPostgres();
        createAndSaveUsersToPostgres();
        createAndSaveShoppingCartsToPostgres();
    }

    private void fireInitialDataToH2() {
        createAndSaveRolesToH2();
        createAndSaveUsersToH2();
        createAndSaveShoppingCartsToH2();
    }

    private void saveRoleToPostgres(Role role){
        if(!this.roleService.existsByName(role.getName())){
            this.roleService.saveRole(role);
        }
    }

    private void createAndSaveRolesToPostgres(){
        Role role1 = new Role("USER");
        Role role2 = new Role("ADMIN");
        saveRoleToPostgres(role1);
        saveRoleToPostgres(role2);
    }

    private void createAndSaveRolesToH2(){
        Role role1 = new Role("USER");
        Role role2 = new Role("ADMIN");
        roleService.saveRole(role1);
        roleService.saveRole(role2);
    }

    private void saveUserToPostgres(UserModel user){
        if(!this.userService.existsByEmail(user.getEmail())){
            this.userService.saveUser(user);
        }
    }

    private void createAndSaveUsersToPostgres(){
        Role userRole = this.roleService.getByName("USER");
        UserModel user1 = new UserModel("userBuyer", "userBuyer@mail.com", "credential");
        user1.addRole(userRole);

        saveUserToPostgres(user1);
    }

    private void createAndSaveUsersToH2(){
        Role userRole = this.roleService.getByName("USER");
        UserModel user1 = new UserModel("userBuyer", "userBuyer@mail.com", "credential");
        user1.addRole(userRole);

        Role userRole2 = this.roleService.getByName("ADMIN");
        UserModel user2 = new UserModel("userAdmin", "userAdmin@mail.com", "credential");
        user1.addRole(userRole2);

        this.userService.saveUser(user1);
        this.userService.saveUser(user2);
    }

    private void createAndSaveShoppingCartsToH2(){
        UserModel user = this.userService.getUserByEmail("userBuyer@mail.com");
        ProductInCart product = new ProductInCart("https://articulo.mercadolibre.com.ar/MLA-1456046051-xiaomi-redmi-note-13-pro-4g-dual-sim-256-gb-purpura-8-gb-ram-_JM",
                                                "Xiaomi Redmi Note 13 Pro 4g Dual Sim 256 Gb  Púrpura 8 Gb Ram",
                                                "MLA1055", 
                                                447814.0, 
                                                "new", 
                                                "MLA1456046051",
                                                1,
                                                "https://http2.mlstatic.com/D_658212-MLA75588485012_042024-O.jpg");
        ShoppingCart newCart = new ShoppingCart(List.of(product), product.getPrice(), user);

        this.productService.saveProduct(product);
        this.shoppingCartService.saveShoppingCart(newCart);
    }

    private void createAndSaveShoppingCartsToPostgres(){
        UserModel userBuyer = this.userService.getUserByEmail("userBuyer@mail.com");
        try {
            this.shoppingCartService.getShoppingCartInProgressByBuyer(userBuyer);
        } catch (HttpException e) {
            ProductInCart product = new ProductInCart("https://articulo.mercadolibre.com.ar/MLA-1456046051-xiaomi-redmi-note-13-pro-4g-dual-sim-256-gb-purpura-8-gb-ram-_JM",
                                                "Xiaomi Redmi Note 13 Pro 4g Dual Sim 256 Gb  Púrpura 8 Gb Ram",
                                                "MLA1055", 
                                                447814.0, 
                                                "new", 
                                                "MLA1456046051",
                                                1,
                                                "https://http2.mlstatic.com/D_658212-MLA75588485012_042024-O.jpg");
            ShoppingCart newCart = new ShoppingCart(List.of(product), product.getPrice(), userBuyer);

            this.productService.saveProduct(product);
            this.shoppingCartService.saveShoppingCart(newCart);
        }
    }

}

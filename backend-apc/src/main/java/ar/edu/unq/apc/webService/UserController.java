package ar.edu.unq.apc.webService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.apc.model.Role;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.security.JWTProvider;
import ar.edu.unq.apc.service.RoleService;
import ar.edu.unq.apc.service.UserService;
import ar.edu.unq.apc.webService.dto.RegisterUserDTO;
import ar.edu.unq.apc.webService.dto.TokenDTO;
import ar.edu.unq.apc.webService.dto.UserDTO;
import ar.edu.unq.apc.webService.dto.UserLoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User services", description = "Manage users of the application")
@RestController
@RequestMapping("/apc")
public class UserController {
    
    @Autowired 
    private UserService userService;

    @Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private RoleService roleService;


    @Operation(summary= "Register a user in the application")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterUserDTO newUser){

        UserModel user = new UserModel(newUser.getUserName(), newUser.getEmail(), newUser.getPassword());
        Role role = this.roleService.getByName("USER");
		user.addRole(role);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(convertUserEntityToUserDTO(this.userService.saveUser(user)));
    } 


    @Operation(summary = "Log in user in the application")
    @PostMapping("/log-in")
	public ResponseEntity<UserDTO> logIn(@RequestBody UserLoginDTO userLogin){

        UserModel user = userService.getUserByEmail(userLogin.getEmail());
		
		Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            userLogin.getEmail(), 
            userLogin.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = this.jwtProvider.generateToken(authentication);
		TokenDTO tokenInfo = new TokenDTO(token);
		
		return ResponseEntity.ok()
                             .header(HttpHeaders.AUTHORIZATION, tokenInfo.getJwtToken())
                             .body(convertUserEntityToUserDTO(user));

    }


    @Operation(summary = "List of users of the application")
    @GetMapping("/admin/all-users")
    public ResponseEntity<List<UserDTO>> allUsers(){
        return ResponseEntity.ok().body(this.userService.registeredUsers().stream()
							               .map(this::convertUserEntityToUserDTO)
                                           .toList());
    }


    @Operation(summary = "Get a user by user mail or user name")
    @GetMapping("/admin/users/search/{search}")
    public ResponseEntity<UserDTO> getUserByEmailOrUserName(@PathVariable String search){
        ResponseEntity<UserDTO> response;
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
        if(search.contains("@")){
            UserModel user = this.userService.getUserByEmail(search);
            response = responseBuilder.body(convertUserEntityToUserDTO(user));
        } else {
            UserModel user = this.userService.getUserByUserName(search);
            response = responseBuilder.body(convertUserEntityToUserDTO(user));
        }
        return response;
    }


    @Operation(summary = "Get all favorite products of logged-in user")
    @GetMapping("/favorite-products")
    public ResponseEntity<List<String>> getAllFavoriteProducts(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken){
        UserModel user = getUserFromToken(authToken);
        return ResponseEntity.ok().body(user.getFavoriteProducts());
    }


    @Operation(summary = "Get all favorite products by user")
    @GetMapping("/admin/{userId}/favorite-products")
    public ResponseEntity<List<String>> getAllFavoriteProductsByUser(@PathVariable String userId){
        UserModel user = this.userService.getUserById(UUID.fromString(userId));
        return ResponseEntity.ok().body(user.getFavoriteProducts());
    }


    @Operation(summary = "Add a favorite product")
    @PutMapping("/favorite-products/add/{productId}")
    public ResponseEntity<List<String>> addFavoriteProduct(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @PathVariable String productId){
        UserModel user = getUserFromToken(authToken);
        user.addFavoriteProduct(productId);
        this.userService.updateUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user.getFavoriteProducts());
    }


    @Operation(summary = "Return true if it is a favorite product of logged-in user")
    @GetMapping("/is-favorite-product/{productId}")
    public ResponseEntity<Boolean> isFavoriteProduct(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @PathVariable String productId){
        UserModel user = getUserFromToken(authToken);
        Boolean result = user.isFavoriteProduct(productId);

        return ResponseEntity.ok().body(result);
    }


    @Operation(summary = "Remove a product from favorites")
    @PutMapping("/favorite-products/remove/{productId}")
    public ResponseEntity<List<String>> removeFavoriteProduct(@Parameter(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @PathVariable String productId){
        UserModel user = getUserFromToken(authToken);
        user.removeFavoriteProduct(productId);
        this.userService.updateUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user.getFavoriteProducts());
    }


    @Operation(summary = "Add the ADMIN role to user")
    @PutMapping("/admin/users/add-admin/{userIdToBeAdmin}")
    public ResponseEntity<UserDTO> addAdminRoleToUser(@PathVariable String userIdToBeAdmin){
        UserModel newAdmin = this.userService.getUserById(UUID.fromString(userIdToBeAdmin));
        Role adminRole = this.roleService.getByName("ADMIN");

        newAdmin.addRole(adminRole);
        this.userService.updateUser(newAdmin);

        return ResponseEntity.ok().body(convertUserEntityToUserDTO(newAdmin));
    }


    @Operation(summary = "Remove the ADMIN role to user")
    @PutMapping("/admin/users/remove-admin/{userIdToRemoveAdmin}")
    public ResponseEntity<UserDTO> removeAdminRoleToUser(@PathVariable String userIdToRemoveAdmin){
        UserModel oldAdmin = this.userService.getUserById(UUID.fromString(userIdToRemoveAdmin));
        Role adminRole = this.roleService.getByName("ADMIN");

        oldAdmin.removeAdminRole(adminRole);
        this.userService.updateUser(oldAdmin);

        return ResponseEntity.ok().body(convertUserEntityToUserDTO(oldAdmin));
    }


    @Operation(summary = "Return true if the user is ADMIN")
    @GetMapping("/users/is-admin/{userId}")
    public ResponseEntity<Boolean> isAdmin(@PathVariable String userId){
        UserModel user = this.userService.getUserById(UUID.fromString(userId));

        return ResponseEntity.ok().body(user.isAdmin());
    }


    public UserDTO convertUserEntityToUserDTO(UserModel user){
        return new UserDTO(user.getId(), user.getUserName(), user.getEmail());
    }


    private UserModel getUserFromToken(String authToken){
        return userService.getUserByEmail(jwtProvider.getUserNameFromJWT(jwtProvider.getTokenWithoutBearerSuffix(authToken)));
    }

}

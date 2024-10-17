package ar.edu.unq.apc.webService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PostMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> allUsers(){
        return ResponseEntity.ok().body(this.userService.registeredUsers().stream()
							               .map(this::convertUserEntityToUserDTO)
                                           .toList());

    }


    public UserDTO convertUserEntityToUserDTO(UserModel user){
        return new UserDTO(user.getId(), user.getUserName(), user.getEmail());
    }

}

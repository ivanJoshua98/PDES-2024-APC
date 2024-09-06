package ar.edu.unq.apc.webService;

import ar.edu.unq.apc.model.User;
import ar.edu.unq.apc.service.impl.BuyerUserService;
import ar.edu.unq.apc.webService.dto.BuyerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class BuyerUserController {

    @Autowired
    private BuyerUserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody BuyerDTO userDto) {
        User newUser = userService.registerUser(userDto.getName(), userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody BuyerDTO userDto) {
        User user = userService.login(userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(user);
    }
}


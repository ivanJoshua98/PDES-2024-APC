package ar.edu.unq.apc.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.model.UserWithMostPurchases;
import ar.edu.unq.apc.persistence.UserRepository;
import ar.edu.unq.apc.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired 
	private PasswordEncoder passwordEncoder;


    @Override
    public List<UserModel> registeredUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserModel saveUser(UserModel user) {
        checkEmailAndUserName(user.getEmail(), user.getUserName());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Override
    public UserModel getUserById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new HttpException("User not found by id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteUserById(UUID id) {
        this.deleteUserById(id);
    }

    @Override
    public UserModel getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new HttpException("User not found by email: " + email, HttpStatus.NOT_FOUND));
    }

    private void checkEmailAndUserName(String email, String userName){
        if(this.userRepository.existsByEmail(email)){
            throw new HttpException("Email already used", HttpStatus.BAD_REQUEST);
        }
        if(this.userRepository.existsByUserName(userName)){
            throw new HttpException("User name already used", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public UserModel updateUser(UserModel user) {
        if (existsByEmail(user.getEmail())){
            return this.userRepository.save(user);
        } else {
            throw new HttpException("User not found", HttpStatus.NOT_FOUND);
        }
        
    }

    @Override
    public UserModel getUserByUserName(String userName) {
        return this.userRepository.findByUserName(userName).orElseThrow(() -> new HttpException("User not found by userName: " + userName, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<UserWithMostPurchases> getUserWithMostPurchases() {
        return this.userRepository.findUsersWithMostPurchases(CartState.SOLD);
    }
    
}

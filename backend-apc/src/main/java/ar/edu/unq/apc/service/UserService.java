package ar.edu.unq.apc.service;

import java.util.List;
import java.util.UUID;

import ar.edu.unq.apc.model.UserModel;
import ar.edu.unq.apc.model.UserWithMostPurchases;

public interface UserService {
    
    List<UserModel> registeredUsers();
    
    UserModel saveUser(UserModel user);
    
    UserModel getUserById(UUID id);
    
    void deleteUserById(UUID id);

    UserModel getUserByEmail(String email);

    Boolean existsByEmail(String email);

    UserModel updateUser(UserModel user);

    UserModel getUserByUserName(String userName);

    List<UserWithMostPurchases> getUserWithMostPurchases();

}

package ar.edu.unq.apc.model;

import java.util.UUID;


public interface UserWithMostPurchases {

    UUID getId();

    String getUserName();

    String getEmail();

    Integer getPurchasesCount();
    
}

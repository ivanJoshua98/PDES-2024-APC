package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserCounterTest {

    private UserCounter anyCounter;

    @BeforeEach
    private void init(){
        this.anyCounter = new UserCounter();
    }


    @Test
    void whenItsSetsTheUserNameThenItsReturnsANewUserNameTest() {
        anyCounter.setUserName("userBuyer0");

        assertEquals("userBuyer0", anyCounter.getUserName());
    }

    @Test
    void whenItsSetsTheUserIdThenItsReturnsANewUserIdTest() {
        UUID anyId = UUID.randomUUID();

        anyCounter.setUserId(anyId);

        assertEquals(anyId, anyCounter.getUserId());
    }

    @Test
    void whenItsSetsTheEmailThenItsReturnsANewUserEmailTest() {
        anyCounter.setEmail("userBuyer0@mail.com");

        assertEquals("userBuyer0@mail.com", anyCounter.getEmail());
    }
}

package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserModelTest {

    private UserModel anyUser;

    @BeforeEach
    public void init() {
        this.anyUser = new UserModel();
    }

    @Test
    void whenItAddsANewRoleOfAnyUserThenItReturnsAnUpdatedListOfRolesTest() {
        Role adminRole = new Role("ADMIN");
        anyUser.addRole(adminRole);

        assertTrue(anyUser.getRoles().contains(adminRole) && anyUser.getRoles().size() == 1);
    }


    @Test
    void whenItSetsTheMailOfAnyUserThenItReturnsANewMailTest() {
        this.anyUser.setEmail("anyUser@mail.com");

        assertEquals(anyUser.getEmail(), "anyUser@mail.com");

    }

    @Test
    void whenItSetsTheIdOfAnyUserThenItReturnsANewIdTest() {
        UUID randomId = UUID.randomUUID();
        this.anyUser.setId(randomId);

        assertEquals(anyUser.getId(), randomId);

    }

    @Test
    void whenItSetsThePasswordOfAnyUserThenItReturnsANewPasswordTest() {
        this.anyUser.setPassword("contrasegura");

        assertEquals(anyUser.getPassword(), "contrasegura");
    }

    @Test
    void whenItSetsTheRolesOfAnyUserThenItReturnsANewRolesListTest() {
        List<Role> roles = List.of(new Role("ADMIN"), new Role("USER"));
        this.anyUser.setRoles(roles);

        assertTrue(anyUser.getRoles().containsAll(roles));
    }

    @Test
    void whenItSetsTheNameOfAnyUserThenItReturnsANewUserNameTest() {
        anyUser.setUserName("Any User");

        assertEquals(anyUser.getUserName(), "Any User");
    }
}

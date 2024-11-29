package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    void anyRoleIsEqualToOtherRoleWhenTheirNamesAreEqual() {
        Role role1 = new Role("USER");
        Role role2 = new Role("USER");

        assertEquals(role1, role2);
    }
}

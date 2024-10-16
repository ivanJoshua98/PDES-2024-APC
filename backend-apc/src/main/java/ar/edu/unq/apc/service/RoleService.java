package ar.edu.unq.apc.service;

import ar.edu.unq.apc.model.Role;

public interface RoleService {
    
    Boolean existsByName(String name);

    Role saveRole(Role role);

    Role getByName(String name);
}

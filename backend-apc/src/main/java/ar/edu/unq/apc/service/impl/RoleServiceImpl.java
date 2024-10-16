package ar.edu.unq.apc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.Role;
import ar.edu.unq.apc.persistence.RoleRepository;
import ar.edu.unq.apc.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public Role getByName(String name) {
        return this.roleRepository.findByName(name).orElseThrow(() -> new HttpException("Role not found by name: " + name, HttpStatus.NOT_FOUND));
    }

    @Override
    public Boolean existsByName(String name) {
        return this.roleRepository.existsByName(name);
    }

    
}

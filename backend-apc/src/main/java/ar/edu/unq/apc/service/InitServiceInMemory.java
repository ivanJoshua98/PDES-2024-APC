package ar.edu.unq.apc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.apc.model.Role;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service
public class InitServiceInMemory {
    
    @Autowired
    private RoleService roleService;

    protected final Log logger = LogFactory.getLog(getClass());

    @PostConstruct
	public void initialize() {
		logger.warn("Init Data Using Postgres DB");
		fireInitialData();
	}

    private void fireInitialData() {
        Role role1 = new Role("USER");
        if(!this.roleService.existsByName(role1.getName())){
            this.roleService.saveRole(role1);
        }

        Role role2 = new Role("ADMIN");
        if(!this.roleService.existsByName(role2.getName())){
            this.roleService.saveRole(role2);
        }
    }

}

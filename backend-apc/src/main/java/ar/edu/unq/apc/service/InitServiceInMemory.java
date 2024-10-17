package ar.edu.unq.apc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.apc.model.Role;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

@Service
public class InitServiceInMemory {
    
    @Value("${spring.datasource.driverClassName}")
	private String className;

    @Autowired
    private RoleService roleService;

    protected final Log logger = LogFactory.getLog(getClass());

    @PostConstruct
	public void initialize() {
        if (className.equals("org.h2.Driver")) {
			logger.warn("Init Data Using H2 DB");
			fireInitialDataFromH2();
		} else {
            logger.warn("Init Data Using Postgres DB");
            fireInitialDataFromPostgres();
        }
		
	}

    private void fireInitialDataFromPostgres() {
        Role role1 = new Role("USER");
        if(!this.roleService.existsByName(role1.getName())){
            this.roleService.saveRole(role1);
        }

        Role role2 = new Role("ADMIN");
        if(!this.roleService.existsByName(role2.getName())){
            this.roleService.saveRole(role2);
        }
    }

    private void fireInitialDataFromH2() {
        Role role1 = new Role("USER");
        Role role2 = new Role("ADMIN");
        this.roleService.saveRole(role1);
        this.roleService.saveRole(role2);

    }

}

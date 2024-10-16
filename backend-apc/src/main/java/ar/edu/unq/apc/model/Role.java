package ar.edu.unq.apc.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Roles")
public class Role {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String name;
	
	public Role() {
		super();
	}
	
	public Role(String role) {
		this.name = role;
	}
	
	public String getName() {
		return name;
	}

	public UUID getId() {
		return id;
	}
	
	

}
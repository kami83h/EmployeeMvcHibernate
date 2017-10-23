package com.jensen.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="locations")
public class Location {
	@Id
	@Column(name="location_id")
	private int id;
	
	@Column(name="location")
	private String location;
	
	@OneToMany(mappedBy = "location", fetch = FetchType.EAGER)
	private Set<Employee> employees = new HashSet<Employee>();
	
	public Location(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

}

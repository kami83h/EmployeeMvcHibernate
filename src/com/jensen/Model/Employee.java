package com.jensen.Model;

public class Employee {
	private int id;
	private String firstname, lastname;
	private int role, location;

	public Employee(int id,String firstname, String lastname, int location, int role) {
		this.setId(id);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setLocation(location);
		this.setRole(role);	
	}
	public Employee(int id,String firstname) {
		this.setId(id);
		this.setFirstname(firstname);
	}
	public Employee(int id) {
		this.setId(id);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getRole() {
		return this.role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getLocation() {
		return this.location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

}

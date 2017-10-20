package com.jensen.Model;

import java.util.*;

import javax.persistence.*;

@Entity(name="employees")
public class Employee {
	
	private int id;
	private String firstname, lastname;
	private int role, location;
	private Date registrationDate;
	private Set<Skill> skills = new HashSet<Skill>(0);
	

	public Employee(int id,String firstname, String lastname, int location, int role, Date registrationDate) {
		this.setId(id);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setLocation(location);
		this.setRole(role);
		this.registrationDate = registrationDate;
	}
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
	public Employee(){}
	@Id
	@Column(name="employee_id")
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(name="first_name")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	@Column(name="last_name")
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	@Column(name="role")
	public int getRole() {
		return this.role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	@Column(name="location")
	public int getLocation() {
		return this.location;
	}

	public void setLocation(int location) {
		this.location = location;
	}
	@ManyToMany
	@JoinTable(name="employee_has_skills",joinColumns ={
			@JoinColumn(name="employee")},inverseJoinColumns = {
					@JoinColumn(name="skill")})
	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}
	@Column(name="registration_date")
	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

}

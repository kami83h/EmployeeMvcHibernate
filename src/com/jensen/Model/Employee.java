package com.jensen.Model;

import java.util.*;
import javax.persistence.*;
/**
 * 
 * This Class is the entity of a employee
 * 
 * @author Kami Hassanzadeh
 * @author Gustav Malm
 *
 */
@Entity(name="employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private int id;

	@Column(name = "first_name")
	private String firstname;

	@Column(name = "last_name")
	private String lastname;

	@Column(name = "role")
	private Integer roleId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "role", insertable = false, updatable = false)
	private Role role;

	@Column(name = "location")
	private Integer locationId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "location", insertable = false, updatable = false)
	private Location location;

	@Column(name = "registration_date")
	private Date registrationDate;

	@ManyToMany
	@JoinTable(name="employee_has_skills",joinColumns ={
			@JoinColumn(name="employee")},inverseJoinColumns = {
					@JoinColumn(name="skill")})
	private Set<Skill> skills = new HashSet<Skill>(0);

	/**
	 * 
	 * @param id This is going to be this object id
	 * @param firstname This is going to be this object FirstName
	 * @param lastname This is going to be this object LastName
	 * @param location This is going to be this object Location
	 * @param role This is going to be this object Role
	 * @param registrationDate This is going to be this object Date
	 */
	public Employee(int id,String firstname, String lastname,Integer location ,Integer role, Date registrationDate) {

		this.setId(id);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setRoleId(role);
		this.setLocationId(location);
		this.registrationDate = registrationDate;
	}
	/**
	 * 
	 * @param firstname This is going to be this object FirstName
	 * @param lastname This is going to be this object LastName
	 * @param location location This is going to be this object Location
	 * @param role role This is going to be this object Role
	 */
	public Employee(String firstname, String lastname,Integer location, Integer role) {

		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setRoleId(role);
		this.setLocationId(location);
	}
	/**
	 * 
	 * @param id This is going to be this object id
	 * @param firstname This is going to be this object FirstName
	 */
	public Employee(int id,String firstname) {
		this.setId(id);
		this.setFirstname(firstname);
	}
	/**
	 * 
	 * @param id This is going to be this object id
	 */
	public Employee(int id) {
		this.setId(id);
	}

	public Employee(){

	}
	/**
	 * 
	 * @return returns this object Id
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * 
	 * @param id Sets this object Id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return returns this object role
	 */
	public Integer getRoleId() {
		return roleId;
	}
	/**
	 * 
	 * @param roleId sets this object roleId
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	/**
	 * 
	 * @return this object firstName
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * 
	 * @param firstname sets this object firstName
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * 
	 * @return this object LastNaem
	 */
	public String getLastname() {
		return this.lastname;
	}
	/**
	 * 
	 * @param lastname sets this object LastName
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * 
	 * @return this object role
	 */
	public Role getRole() {
		return this.role;
	}
	/**
	 * 
	 * @param role sets this object role
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	/**
	 * 
	 * @return this object location
	 */
	public Location getLocation() {
		return this.location;
	}
	/**
	 * 
	 * @param location sets object locations
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * 
	 * @return this object sets Of Skills
	 */
	public Set<Skill> getSkills() {
		return skills;
	}
	/**
	 * 
	 * @param skills sets this object set of skills
	 */
	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}
	/**
	 * 
	 * @return this object Date
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}
	/**
	 * 
	 * @param registrationDate sets this object Date
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	/**
	 * 
	 * @return return this object locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}
	/**
	 * 
	 * @param locationId sets this object locationId
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

}

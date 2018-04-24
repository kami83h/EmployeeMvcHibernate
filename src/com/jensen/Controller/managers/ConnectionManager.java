package com.jensen.Controller.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import com.jensen.Model.Employee;
import com.jensen.Model.Location;
import com.jensen.Model.Role;
import com.jensen.Model.Skill;
/**
 * 
 * This Class sets up database connection and hibernate session config
 * 
 * @author Kami Hassanzadeh
 * @author Gustav Malm
 *
 */
public class ConnectionManager {

	private Session session;

	/**
	 * This Constructor calls for a init() method to set up the required Session.
	 */
	public ConnectionManager() {
		init();
	}
	/**
	 * Initializes and builds the Hibernate-Orm Database Connection Session.
	 */
	public void init() {
		this.session = new Configuration().configure()
				.addAnnotatedClass(Employee.class)
				.addAnnotatedClass(Skill.class)
				.addAnnotatedClass(Role.class)
				.addAnnotatedClass(Location.class)
				.buildSessionFactory().openSession();
	}
	/**
	 * 
	 * @return Returns this instance of this Object's Session.
	 */
	public Session getSession(){
		return this.session;
	}
}

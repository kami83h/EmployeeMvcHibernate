package com.jensen.Controller.managers;

import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jensen.Model.Employee;
import com.jensen.Model.Role;
import com.jensen.Model.Skill;

public class EmployeeManager extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryManager queryManager;

	public EmployeeManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}
	/* Create new Employee */
	public void addEmployee(String fname, String lname, String role, String location, String skill) {
		if (fname.matches(".*\\d+.*") || lname.matches(".*\\d+.*")) {
			JOptionPane.showMessageDialog(null, "Number is not allowed, try again.");
		} 
		else {
			Date date = new Date();
			
			
			Employee employee = new Employee(queryManager.generateId(), fname, lname,Integer.parseInt(role), Integer.parseInt(location),
					date);
			Skill skills = new Skill(Integer.parseInt(skill));
			queryManager.insertInto(employee,skills);
			queryManager.showAllEmployee();
		}
	}

	/* Delete Employee */
	public void deleteEmployee(String id) {
		
		if (id.isEmpty() && id.contains(null)) {

		} else {
			queryManager.deleteEmployee(id);
			queryManager.showAllEmployee();
		}
	}
	
	/* Update Employee */
	public void updateEmployee(String id, String fn) {
		
		Employee employee = new Employee(Integer.parseInt(id), fn);
		
		queryManager.updateEmployee(employee);
		queryManager.getAllRowsOnlyName();
	}
}
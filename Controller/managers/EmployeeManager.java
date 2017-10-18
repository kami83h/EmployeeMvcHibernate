package com.jensen.Controller.managers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.jensen.Model.Employee;

import java.awt.*;
import java.sql.*;

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
			Employee employee = new Employee(queryManager.generateId(), fname, lname, Integer.parseInt(location),
					Integer.parseInt(role));
			queryManager.insertInto(employee,skill);
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
		System.out.println(employee.getFirstname());
		queryManager.updateEmployee(employee);
		queryManager.getAllRowsOnlyName();
	}
}
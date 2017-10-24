package com.jensen.Controller.managers;

import javax.swing.JOptionPane;
import com.jensen.Model.Employee;
/**
 * 
 * This Class manages the employee object
 * 
 * @author Kami Hassanzadeh
 * @author Gustav Malm
 *
 */
public class EmployeeManager{

	private static final long serialVersionUID = 1L;

	private QueryManager queryManager;

	/**
	 * 
	 * @param queryManager init a querymanager
	 */
	public EmployeeManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}
	/**
	 * Create new Employee
	 * @param fname This is going to be this object FirstName
	 * @param lname This is going to be this object LastName
	 * @param role This is going to be this object Role
	 * @param location This is going to be this object Location
	 * @param skill This is going to be this object Skill
	 */
	public void addEmployee(String fname, String lname, String role, String location, String skill) {

		if (fname.matches(".*\\d+.*") || lname.matches(".*\\d+.*")) {
			JOptionPane.showMessageDialog(null, "Number is not allowed, try again.");
		} 
		else {

			Employee employee = new Employee( fname, lname,
					Integer.parseInt(location),Integer.parseInt(role));
			this.queryManager.insertInto(employee,skill);
			this.queryManager.showAllEmployee();
		}
	}
	/**
	 * 
	 * @param id remove employee from database
	 */
	public void deleteEmployee(String id) {

		if (id.isEmpty() && id.contains(null)) {

		} else {
			this.queryManager.deleteEmployee(id);
			this.queryManager.showAllEmployee();
		}
	}
	/**
	 * @param id find the specific id from input
	 * @param fn change the firstname
	 */
	public void updateEmployee(String id, String fn) {

		Employee employee = new Employee(Integer.parseInt(id), fn);

		this.queryManager.updateEmployee(employee);
		this.queryManager.getAllRowsOnlyName();
	}
}
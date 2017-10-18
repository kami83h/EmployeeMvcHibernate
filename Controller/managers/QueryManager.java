package com.jensen.Controller.managers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import com.jensen.Model.Employee;

public class QueryManager {

	private Connection connection;
	private DefaultTableModel model;
	private String employeeId= "", fname= "", lname = "", location = "", role = "", registration_date = "";


	public QueryManager(Connection connection, DefaultTableModel model){
		this.connection = connection;
		this.model = model;

	}
	public QueryManager(){

	}
	/* Returns all Employees and places it into the JTableModel */
	public void showAllEmployee() {
		update();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("select * from employees");
			ResultSet result = ps.executeQuery();
			model.addRow(new Object[]{"employee_id", "first_name", "last_name", "location","role", "registration_date"});
			while(result.next())
			{
				employeeId = result.getString("employee_id");
				fname = result.getString("first_name");
				lname = result.getString("last_name");
				location = result.getString("location"); 
				role = result.getString("role");
				registration_date = result.getString("registration_date");
				model.addRow(new Object[]{employeeId, fname, lname, location, role, registration_date});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* Returns all Employees with only employee_id and first_name and places it into the JTableModel */
	public void getAllRowsOnlyName() {
		update();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("SELECT employee_id, first_name FROM employees");
			ResultSet result = ps.executeQuery();

			model.addRow(new Object[]{"employee_id", "first_name"});
			while(result.next())
			{
				employeeId = result.getString("employee_id");
				fname = result.getString("first_name");
				model.addRow(new Object[]{employeeId, fname});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	/* Inserts a new Employee into the Database */
	public void insertInto(Employee employee, String skill) {
		CallableStatement call;
		try {
			call = connection.prepareCall("{call insert_user( ?, ?, ?, ?, ?)}");
			call.setInt(1, employee.getId());
			call.setString(2, employee.getFirstname());
			call.setString(3, employee.getLastname());
			call.setInt(4, employee.getLocation());
			call.setInt(5, employee.getRole());
			call.executeQuery();
			insertEmployeeAndSkill(employee,skill);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void insertEmployeeAndSkill(Employee employee, String skill){
		String quary = "INSERT INTO employee_has_skills values ("+employee.getId()+","+Integer.parseInt(skill)+")";
		try {
			PreparedStatement preparedStmt = connection.prepareStatement(quary);
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Removes a Employee from the Database */
	public void deleteEmployee(String id) {
		update();
		String removeQuary = "DELETE FROM employee_has_skills WHERE employee ='" + Integer.parseInt(id) + "'";
		String query = "DELETE FROM employees WHERE employee_id ='" + Integer.parseInt(id) + "'";
		PreparedStatement state;
		PreparedStatement preparedStmt;

		try {
			if (id.isEmpty() && id.contains(null)) {
				System.out.println("Delete Employee not deleted");
			}else{
				state = connection.prepareStatement(removeQuary);
				preparedStmt = connection.prepareStatement(query);
				state.execute();
				preparedStmt.execute();
				//showAllEmployee();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/* Updates a Employee from the Database */
	public void updateEmployee(Employee employee) {
		update();
		String query = " UPDATE employees SET first_name='" + employee.getFirstname() + "' WHERE employee_id=" + employee.getId();
		PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement(query);
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* Generates a ID */
	public int generateId() {
		String query = " SELECT employee_id FROM employees GROUP BY employee_id DESC";
		int generatedId = 0;
		Statement state;
		try {
			state = connection.createStatement();
			ResultSet result = state.executeQuery(query);
			result.first();
			generatedId = result.getInt("employee_id") + 1;
			System.out.println(generatedId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generatedId;		
	}
	public void update(){
		while(model.getRowCount() > 0){
			model.removeRow(0);
		}
	}
}

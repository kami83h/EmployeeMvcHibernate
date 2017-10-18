package com.jensen.Controller.managers;

import java.sql.*;
import java.util.*;

import javax.swing.table.DefaultTableModel;

import com.jensen.Model.Employee;

public class QueryManager {

	private Connection connection;
	private DefaultTableModel model;
	private String employeeId,fname,lname,skill,skillId,
	roleId,locationId,location,town,role,registration_date;

	private List<String> list = new LinkedList<String>(); 

	public QueryManager(Connection connection, DefaultTableModel model){
		this.connection = connection;
		this.model = model;
		init();
	}
	private void init(){
		list.add("EmployeeId");
		list.add("Firstname");
		list.add("Lastname");
		list.add("Role");
		list.add("Skill");
		list.add("RoleId");
		list.add("SkillId");
		list.add("Location");
		list.add("LocationId");
		list.add("RegistrationDate");
		list.add("Town");

	}
	/* Returns all Employees and places it into the JTableModel */
	public void showAllEmployee() {
		update();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("select * from employees");
			ResultSet result = ps.executeQuery();

			model.addRow(new Object[]{list.get(list.indexOf("EmployeeId")),
					list.get(list.indexOf("Firstname")),
					list.get(list.indexOf("Lastname")),
					list.get(list.indexOf("Location")),
					list.get(list.indexOf("Role")),
					list.get(list.indexOf("RegistrationDate"))});

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

			model.addRow(new Object[]{
					list.get(list.indexOf("EmployeeId")),
					list.get(list.indexOf("Firstname"))});
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
	/* Returns a Employee with the same name as the Input parameter and places it into the JTableModel */
	public void getEmployeeByName(String input) {
		update();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					"" + "SELECT * FROM employees WHERE first_name LIKE '%" + input + "%' OR last_name LIKE '%" + input + "%'");
			ResultSet result = ps.executeQuery();

			model.addRow(new Object[]{list.get(list.indexOf("EmployeeId")),
					list.get(list.indexOf("Firstname")),
					list.get(list.indexOf("Lastname"))});
			while (result.next()) {
				employeeId = result.getString("employee_id");
				fname = result.getString("first_name");
				lname = result.getString("last_name");
				model.addRow(new Object[] { employeeId, fname, lname });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* Returns a Employee with the Location of the Input parameter and places it into the JTableModel */
	public void getEmployeeByLocation(String input) {
		update();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					"SELECT first_name, last_name, town FROM employees JOIN locations ON employees.location = location_id WHERE location_id="
							+ input);
			ResultSet result = ps.executeQuery();

			model.addRow(new Object[]{
					list.get(list.indexOf("Firstname")),
					list.get(list.indexOf("Lastname")),
					list.get(list.indexOf("Town"))});
			while (result.next()) {
				fname = result.getString("first_name");
				lname = result.getString("last_name");
				town = result.getString("town");
				model.addRow(new Object[] { fname, lname, town });
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* Returns all Locations and places it into the JTableModel */
	public void getAllLocation() {
		update();
		Statement state;
		System.out.println("here");
		try {
			state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM locations");

			model.addRow(new Object[]{list.get(list.indexOf("LocationId")),
					list.get(list.indexOf("Town"))});

			while (result.next()) {
				locationId = result.getString("location_id");
				town = result.getString("town");
				model.addRow(new Object[] { locationId, town });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* Returns a Employee with the Role of the Input parameter and places it into the JTableModel */
	public void getEmployeeByRole(String input) {
		update();
		try {

			PreparedStatement ps = connection.prepareStatement(
					"SELECT first_name, last_name, roles.role FROM employees JOIN roles ON employees.role = role_id WHERE role_id="
							+ input);
			ResultSet result = ps.executeQuery();

			model.addRow(new Object[]{
					list.get(list.indexOf("Firstname")),
					list.get(list.indexOf("Lastname")),
					list.get(list.indexOf("Role"))});

			while (result.next()) {
				fname = result.getString("first_name");
				lname = result.getString("last_name");
				role = result.getString("role");
				model.addRow(new Object[] { fname, lname, role });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* Returns all Roles and places it into the JTableModel */
	public void getAllRole() {
		update();
		Statement state;
		try {
			state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM roles");

			model.addRow(new Object[]{list.get(list.indexOf("RoleId")),
					list.get(list.indexOf("Role"))});

			while (result.next()) {
				roleId = result.getString("role_id");
				role = result.getString("role");
				model.addRow(new Object[] { roleId, role });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* Returns a Employee with the Skill of the Input parameter and places it into the JTableModel */
	public void getEmployeeBySkill(String input) {
		update();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"SELECT first_name, last_name, Skills.skill FROM employees JOIN skills INNER JOIN employee_has_skills ON employee_id=employee AND skill_id=employee_has_skills.skill WHERE skill_id="
							+ input);
			ResultSet result = ps.executeQuery();
			
			model.addRow(new Object[]{
					list.get(list.indexOf("Firstname")),
					list.get(list.indexOf("Lastname")),
					list.get(list.indexOf("Skill"))});
			
			while (result.next()) {
				fname = result.getString("first_name");
				lname = result.getString("last_name");
				skill = result.getString("skill");
				model.addRow(new Object[] { fname, lname, skill });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* Returns all Skills and places it into the JTableModel */
	public void getAllSkills() {
		update();
		Statement state;
		try {
			state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM skills");
			
			model.addRow(new Object[]{list.get(list.indexOf("SkillId")),
					list.get(list.indexOf("Skill"))});

			while (result.next()) {
				skillId = result.getString("skill_id");
				skill = result.getString("skill");
				model.addRow(new Object[] { skillId, skill });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package com.jensen.Controller.managers;

import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Handler {
	private JFrame frame1 = new JFrame("Database Search Result");
	private Connection connection;
	private static JTable table = new JTable();
	private DefaultTableModel model = new DefaultTableModel();
	private JScrollPane scroll = new JScrollPane();
	private String[] columnNamesAll = { "", "", "" };
	private String employeeid = "", fname = "", lname = "", roleid = "", role = "", skillid = "", skill = "", locationid = "",
			town = "";
	
	/* Main constructor for Handler */
	public Handler(Connection connection) {
		this.connection = connection;
	}

	/* Returns a Employee with the same name as the Input parameter and places it into the JTableModel */
	public void getEmployeeByName(String input) {
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					"" + "SELECT * FROM employees WHERE first_name='" + input + "' OR last_name='" + input + "'");
			ResultSet result = ps.executeQuery();
			showAll();
			model.addRow(new Object[] { "employee_id", "first_name", "last_name" });
			while (result.next()) {
				employeeid = result.getString("employee_id");
				fname = result.getString("first_name");
				lname = result.getString("last_name");
				model.addRow(new Object[] { employeeid, fname, lname });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Returns a Employee with the Location of the Input parameter and places it into the JTableModel */
	public void getEmployeeByLocation(String input) {
		
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					"SELECT first_name, last_name, town FROM employees JOIN locations ON employees.location = location_id WHERE location_id="
							+ input);
			ResultSet result = ps.executeQuery();
			showAll();
			model.addRow(new Object[] { "first_name", "last_name", "town" });
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

	/* Returns a Employee with the Role of the Input parameter and places it into the JTableModel */
	public void getEmployeeByRole(String input) {
		try {
			getAllRole();
			PreparedStatement ps = connection.prepareStatement(
					"SELECT first_name, last_name, roles.role FROM employees JOIN roles ON employees.role = role_id WHERE role_id="
							+ input);
			ResultSet result = ps.executeQuery();
			showAll();
			model.addRow(new Object[] { "first_name", "last_name", "role" });
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

	/* Returns a Employee with the Skill of the Input parameter and places it into the JTableModel */
	public void getEmployeeBySkill(String input) {
		try {
			getAllSkills();
			PreparedStatement ps = connection.prepareStatement(
					"SELECT first_name, last_name, Skills.skill FROM employees JOIN skills INNER JOIN employee_has_skills ON employee_id=employee AND skill_id=employee_has_skills.skill WHERE skill_id="
							+ input);
			ResultSet result = ps.executeQuery();
			showAll();
			model.addRow(new Object[] { "first_name", "last_name", "skill" });
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

	/* Creates a new JFrame and adds the JTable and Model into it. Temporary Solution and will be removed in the upcoming patch */
	public void showAll() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		
		frame1 = new JFrame("Database Search Result");
		frame1.setLayout(new BorderLayout());
		model.setColumnIdentifiers(columnNamesAll);

		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
		scroll = new JScrollPane(table);
		frame1.add(scroll);
		frame1.setVisible(true);
		frame1.setSize(600, 300);
		frame1.setLocationRelativeTo(null);
	}
	
	/* Returns all Skills and places it into the JTableModel */
	public void getAllSkills() {
		Statement state;
		try {
			state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM skills");
			showAll();
			model.addRow(new Object[] { "skill_id", "skill" });

			while (result.next()) {
				skillid = result.getString("skill_id");
				skill = result.getString("skill");
				model.addRow(new Object[] { skillid, skill });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Returns all Roles and places it into the JTableModel */
	public void getAllRole() {
		Statement state;
		try {
			state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM roles");
			showAll();
			model.addRow(new Object[] { "role_id", "role" });
			while (result.next()) {
				roleid = result.getString("role_id");
				role = result.getString("role");
				model.addRow(new Object[] { roleid, role });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Returns all Locations and places it into the JTableModel */
	public void getAllLocation() {
		Statement state;
		System.out.println("here");
		try {
			state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM locations");
			showAll();
			model.addRow(new Object[] { "location_id", "town" });
			while (result.next()) {
				locationid = result.getString("location_id");
				town = result.getString("town");
				model.addRow(new Object[] { locationid, town });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
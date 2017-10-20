package com.jensen.Controller.managers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaQuery;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

import com.jensen.Model.Employee;
import com.jensen.Model.Skill;

public class QueryManager {

	private Connection connection;
	private Session session;
	private DefaultTableModel model;
	private String employeeId,fname,lname,skill,skillId,
	roleId,locationId,location,town,role,registration_date;
	private ArrayList<Employee> arraylist = new ArrayList<Employee>();
	private List<Employee> employees;

	private List<String> list = new LinkedList<String>(); 

	public QueryManager(Connection connection, DefaultTableModel model, Session session){
		this.connection = connection;
		this.model = model;
		this.session = session;
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
		model.addRow(new Object[]{list.get(list.indexOf("EmployeeId")),
				list.get(list.indexOf("Firstname")),
				list.get(list.indexOf("Lastname")),
				list.get(list.indexOf("Location")),
				list.get(list.indexOf("Role")),
				list.get(list.indexOf("RegistrationDate"))});

		for(Employee employee : employees){
			model.addRow(new Object[]{employee.getId(),employee.getFirstname(),employee.getLastname(),
					employee.getLocation(),employee.getRole(),employee.getRegistrationDate()});
		}

	}
	/* Returns all Employees with only employee_id and first_name and places it into the JTableModel */
	public void getAllRowsOnlyName() {
		update();

		model.addRow(new Object[]{
				list.get(list.indexOf("EmployeeId")),
				list.get(list.indexOf("Firstname"))});

		for(Employee employee : employees){
			model.addRow(new Object[]{employee.getId(),employee.getFirstname()});
		}
	}


	/* Inserts a new Employee into the Database */
	public void insertInto(Employee employee, Skill skill) {
		session.beginTransaction();

		Set<Skill> skills = new HashSet<Skill>();
		skills.add(skill);

		employee.setSkills(skills);
		session.save(employee);
		session.getTransaction().commit();

	}
	/* Removes a Employee from the Database */
	public void deleteEmployee(String id) {
		update();
		session.beginTransaction();

		Employee result = session.load(Employee.class, Integer.parseInt(id));
		
		session.delete(result);
		session.getTransaction().commit();
	

	}
	/* Updates a Employee from the Database */
	public void updateEmployee(Employee employee) {

		update();

		session.beginTransaction();

		Employee result = session.load(Employee.class, employee.getId());
		result.setFirstname(employee.getFirstname());
		session.update(result);

		session.getTransaction().commit();

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

		CriteriaQuery<Employee> result = session.getCriteriaBuilder().createQuery(Employee.class);
		result.from(Employee.class);
		employees = session.createQuery(result).getResultList();

	}
	/* Returns a Employee with the same name as the Input parameter and places it into the JTableModel */
	public void getEmployeeByName(String input) {
		update();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(
					"" + "SELECT * FROM employees WHERE first_name LIKE '%" 
							+ input + "%' OR last_name LIKE '%" + input + "%'");

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

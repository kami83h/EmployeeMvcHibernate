package com.jensen.Controller.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaQuery;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import com.jensen.Model.Employee;
import com.jensen.Model.Location;
import com.jensen.Model.Role;
import com.jensen.Model.Skill;

public class QueryManager {

	private Connection connection;
	private Session session;
	private DefaultTableModel model;
	private String employeeId,fname,lname,skill,skillId,
	roleId,locationId,town,role,registration_date;
	private List<Employee> employees;
	private List<Location> locations;
	private List<Role> roles;
	private List<Skill> skills;
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

		updateListOfEmployees();
		model.addRow(new Object[]{list.get(list.indexOf("EmployeeId")),
				list.get(list.indexOf("Firstname")),
				list.get(list.indexOf("Lastname")),
				list.get(list.indexOf("Location")),
				list.get(list.indexOf("Role")),
				list.get(list.indexOf("RegistrationDate"))});

		for(Employee employee : employees){
			model.addRow(new Object[]{employee.getId(),employee.getFirstname(),employee.getLastname(),
					employee.getLocation().getLocation(),employee.getRole().getRole(),employee.getRegistrationDate()});
		}
	}
	/* Returns all Employees with only employee_id and first_name and places it into the JTableModel */
	public void getAllRowsOnlyName() {
		updateListOfEmployees();

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
		updateListOfEmployees();
		session.beginTransaction();

		Employee result = session.load(Employee.class, Integer.parseInt(id));

		session.delete(result);
		session.getTransaction().commit();


	}
	/* Updates a Employee from the Database */
	public void updateEmployee(Employee employee) {

		updateListOfEmployees();

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
	public void updateListOfEmployees(){
		while(model.getRowCount() > 0){
			model.removeRow(0);
		}
		CriteriaQuery<Employee> result = session.getCriteriaBuilder().createQuery(Employee.class);
		result.from(Employee.class);
		employees = session.createQuery(result).getResultList();
	}
	public void updateListOfLocations(){
		while(model.getRowCount() > 0){
			model.removeRow(0);
		}
		CriteriaQuery<Location> result = session.getCriteriaBuilder().createQuery(Location.class);
		result.from(Location.class);
		locations = session.createQuery(result).getResultList();
	}
	public void updateListOfRoles(){
		while(model.getRowCount() > 0){
			model.removeRow(0);
		}
		CriteriaQuery<Role> result = session.getCriteriaBuilder().createQuery(Role.class);
		result.from(Role.class);
		roles = session.createQuery(result).getResultList();
	}
	public void updateListOfSkills(){
		while(model.getRowCount() > 0){
			model.removeRow(0);
		}
		CriteriaQuery<Skill> result = session.getCriteriaBuilder().createQuery(Skill.class);
		result.from(Skill.class);
		skills = session.createQuery(result).getResultList();
	}
	/* Returns a Employee with the same name as the Input parameter and places it into the JTableModel */
	public void getEmployeeByName(String input) {
		updateListOfEmployees();

		model.addRow(new Object[]{list.get(list.indexOf("EmployeeId")),
				list.get(list.indexOf("Firstname")),
				list.get(list.indexOf("Lastname"))});

		for(Employee employee : employees){

			if(employee.getFirstname().contains(input)||employee.getLastname().contains(input)){
				model.addRow(new Object[]{employee.getId(),employee.getFirstname(),employee.getLastname()});
			}
		}
	}
	/* Returns a Employee with the Location of the Input parameter and places it into the JTableModel */
	public void getEmployeeByLocation(String input) {
		updateListOfEmployees();
		model.addRow(new Object[]{
				list.get(list.indexOf("Firstname")),
				list.get(list.indexOf("Lastname")),
				list.get(list.indexOf("Town"))});

		for(Employee employee : employees){
			if(input.equals(employee.getLocationId().toString())){
				model.addRow(new Object[] { employee.getFirstname(), employee.getLastname(), employee.getLocation().getLocation() });
			}
		}
	}
	/* Returns all Locations and places it into the JTableModel */
	public void getAllLocation() {
		updateListOfLocations();

		model.addRow(new Object[]{list.get(list.indexOf("LocationId")),
				list.get(list.indexOf("Town"))});
		for(Location location : locations){
			model.addRow(new Object[]{location.getId(),location.getLocation()});
		}

	}
	/* Returns a Employee with the Role of the Input parameter and places it into the JTableModel */
	public void getEmployeeByRole(String input) {
		updateListOfEmployees();

		model.addRow(new Object[]{
				list.get(list.indexOf("Firstname")),
				list.get(list.indexOf("Lastname")),
				list.get(list.indexOf("Role"))});

		for(Employee employee : employees){
			if(input.equals(employee.getRoleId().toString())){
				model.addRow(new Object[] { employee.getFirstname(), employee.getLastname(), employee.getRole().getRole() });
			}
		}
	}
	/* Returns all Roles and places it into the JTableModel */
	public void getAllRole() {
		updateListOfRoles();

		model.addRow(new Object[]{list.get(list.indexOf("RoleId")),
				list.get(list.indexOf("Role"))});

		for(Role role : roles){
			model.addRow(new Object[]{role.getId(),role.getRole()});
		}
	}
	/* Returns a Employee with the Skill of the Input parameter and places it into the JTableModel */
	public void getEmployeeBySkill(String input) {
		updateListOfEmployees();
		updateListOfSkills();

		//		try {
		//			PreparedStatement ps = connection.prepareStatement(
		//					"SELECT first_name, last_name, Skills.skill FROM employees JOIN skills INNER JOIN employee_has_skills ON employee_id=employee AND skill_id=employee_has_skills.skill WHERE skill_id="
		//							+ input);
		//			ResultSet result = ps.executeQuery();
		//
		model.addRow(new Object[]{
				list.get(list.indexOf("Firstname")),
				list.get(list.indexOf("Lastname")),
				list.get(list.indexOf("Skill"))});

		for(Skill skill : skills){
			for(Employee employee : employees){
				if(Integer.parseInt(input) == skill.getId()){
					System.out.println(skill.getEmployee().iterator().next());
					model.addRow(new Object[] { employee.getFirstname(), employee.getLastname(), skill.getSkill() });
				}
			}
		}
	}
	//
	//			while (result.next()) {
	//				fname = result.getString("first_name");
	//				lname = result.getString("last_name");
	//				skill = result.getString("skill");
	//				model.addRow(new Object[] { fname, lname, skill });
	//			}
	//		} catch (SQLException e) {
	//			e.printStackTrace();
	//		}

	/* Returns all Skills and places it into the JTableModel */
	public void getAllSkills() {
		updateListOfSkills();
		model.addRow(new Object[]{list.get(list.indexOf("SkillId")),
				list.get(list.indexOf("Skill"))});

		for(Skill skill : skills){
			model.addRow(new Object[]{skill.getId(),skill.getSkill()});
		}
	}
}

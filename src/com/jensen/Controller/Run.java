package com.jensen.Controller;

import java.util.*;
import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import com.jensen.Model.Employee;
import com.jensen.Model.Skill;

public class Run {

	public static void main(String[] args) {	
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		new Application();
//		Session session = new Configuration().configure()
//				.addAnnotatedClass(Employee.class)
//				.addAnnotatedClass(Skill.class)
//				.buildSessionFactory().openSession();
		
////		session.beginTransaction();
//		
//		Employee employee = new Employee(26,"nytt","testigen",2,1);
//		
//		Skill skill = new Skill(2);
//		Skill skillone = new Skill(1);
//		
//		Set<Skill> skills = new HashSet<Skill>();
//		skills.add(skill);
//		skills.add(skillone);
//		
//		employee.setSkills(skills);
//		
//		session.save(employee);
//		session.getTransaction().commit();
		
	}
}

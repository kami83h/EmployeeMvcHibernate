package com.jensen.Model;

import java.util.*;
import javax.persistence.*;

@Entity(name="skills")
public class Skill {

	private int id;
	private String skill;
	private Set<Employee> employee = new HashSet<Employee>(0);

	public Skill(int id){
		this.id = id;
		
	}
	public Skill(){}
	@Id
	@Column(name="skill_id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="skill")
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	@ManyToMany
	public Set<Employee> getEmployee() {
		return employee;
	}
	public void setEmployee(Set<Employee> employee) {
		this.employee = employee;
	}


}

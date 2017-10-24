package com.jensen.Controller.managers;

import java.util.*;

import com.jensen.Model.Role;
/**
 * 
 * This Class makes sure that application runs
 * 
 * @author Kami Hassanzadeh
 * @author Gustav Malm
 *
 */
public class RoleManager {

	private List<Role> roles = new LinkedList<Role>();

	public RoleManager(){}

	public List<Role> getRoles(){
		return this.roles;
	}

	public Role getRoleById(int id){
		for(Role role :roles){
			if(id == role.getId()){
			return this.getRoles().get(id);
			}
		}
		return null;
	}

}

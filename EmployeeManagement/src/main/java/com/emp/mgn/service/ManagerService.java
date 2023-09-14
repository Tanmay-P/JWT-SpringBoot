package com.emp.mgn.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emp.mgn.entity.Manager;
import com.emp.mgn.repository.ManagerRepository;

@Service
@Transactional
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	//add manager
	public Manager saveManager(Manager manager) throws Exception {
		Optional<Manager> optManager = managerRepository.findByUsername(manager.getUsername());
		if(optManager.isPresent()) {
			throw new Exception("Username already exists");
		}
		return managerRepository.save(manager);
	}
	
	
	//update manager
	public Manager updateManager(Manager manager) throws Exception {
		managerRepository.findById(manager.getId()).orElseThrow(() -> new Exception("Manager Not Found with Id : " + manager.getId()));
		//manager.setPassword(passwordEncoder.encode(manager.getPassword()));
		return managerRepository.save(manager);
	}
	
	//delete manager
	public void deleteManager(int empId) throws Exception {
		managerRepository.findById(empId).orElseThrow(() -> new Exception("Manager Not Found with Id : " + empId));
		managerRepository.deleteById(empId);
	}
	
	//get manager by Id
	public Manager getManagerById(int empId) throws Exception {
		Manager manager = managerRepository.findById(empId).orElseThrow(() -> new Exception("Manager Not Found with Id : " + empId));
		//manager.setPassword(passwordEncoder.encode(manager.getPassword()));
		return manager;
	}
	
	//get all managers
	public List<Manager> getAllManagers() {
		List<Manager> list = managerRepository.findAll();
	//	list.forEach(e -> e.setPassword(passwordEncoder.encode(e.getPassword())));
		return list;
	}
	
	//login manager
	public Manager loginManager(String username, String password) throws Exception {
		Manager manager = managerRepository.findByUsername(username).orElseThrow(() -> new Exception("Manager Not Found with username : " + username));
		
		//boolean isPasswordMatch = passwordEncoder.matches(password, manager.getPassword());
		
		if(!manager.getPassword().equals(password)) {
			throw new Exception("Password Invalid");
		}
		return manager;
	}
}

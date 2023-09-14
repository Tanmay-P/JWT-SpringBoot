package com.emp.mgn.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.mgn.entity.Admin;
import com.emp.mgn.repository.AdminRepository;

@Service
@Transactional
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	//add admin
	public Admin saveAdmin(Admin admin) throws Exception {
		Optional<Admin> optAdmin = adminRepository.findByUsername(admin.getUsername());
		if(optAdmin.isPresent()) {
			throw new Exception("Username already exist");
		}
		return adminRepository.save(admin);
	}
	
	//update admin
	public Admin updateAdmin(Admin admin) throws Exception {
		adminRepository.findById(admin.getId()).orElseThrow(() -> new Exception("Admin not found with id : " + admin.getId()));
		return adminRepository.save(admin);
	}
	
	//delete admin
	public void deleteAdmin(int adminId) throws Exception {
		adminRepository.findById(adminId).orElseThrow(() -> new Exception("Admin not found with id : " + adminId));
		adminRepository.deleteById(adminId);
	}
	
	//get admin by id
	public Admin getAdminById(int adminId) throws Exception {
		return adminRepository.findById(adminId).orElseThrow(() -> new Exception("Admin not found with id : " + adminId));
	}
	
	//get all admin
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}
	
	//admin login
	public Admin login(String username, String password) throws Exception {
		Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new Exception("Invalid Username."));
		if(!admin.getPassword().equals(password)) {
			throw new Exception("Invalid Password.");
 		}
		return admin;
	}
	
}

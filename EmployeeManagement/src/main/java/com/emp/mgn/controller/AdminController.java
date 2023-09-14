package com.emp.mgn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.mgn.entity.Admin;
import com.emp.mgn.model.AuthRequest;
import com.emp.mgn.service.AdminService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/add")
	public ResponseEntity<Object> addAdmin(@RequestBody Admin admin) {
		try {
			adminService.saveAdmin(admin);
			return ResponseEntity.ok("Admin added");
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateAdmin(@RequestBody Admin admin) {
		try {
			adminService.updateAdmin(admin);
			return ResponseEntity.ok("Admin updated");
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{adId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> deleteAdmin(@PathVariable int adId) {
		try {
			adminService.deleteAdmin(adId);
			return ResponseEntity.ok("Deleted");
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/get/{adId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getAdmin(@PathVariable int adId) {
		try {
			Admin admin = adminService.getAdminById(adId);
			return ResponseEntity.ok(admin);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> allAdmins() {
		try {
			List<Admin> list = adminService.getAllAdmins();
			return ResponseEntity.ok(list);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody AuthRequest authRequest) {
		try {
			Admin admin = adminService.login(authRequest.getUsername(), authRequest.getPassword());
			return ResponseEntity.ok(admin);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}

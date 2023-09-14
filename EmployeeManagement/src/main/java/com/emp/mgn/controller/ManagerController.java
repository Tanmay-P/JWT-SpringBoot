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

import com.emp.mgn.entity.Manager;
import com.emp.mgn.model.AuthRequest;
import com.emp.mgn.service.ManagerService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	
	@PostMapping("/add")
	public ResponseEntity<Object> addManager(@RequestBody Manager manager) {
		try {
			managerService.saveManager(manager);
			return ResponseEntity.ok("Manager added");
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Object> updateManager(@RequestBody Manager manager) {
		try {
			managerService.updateManager(manager);
			return ResponseEntity.ok("Manager updated");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{mngId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> deleteManager(@PathVariable int mngId) {
		try {
			managerService.deleteManager(mngId);
			return ResponseEntity.ok("Manager Deleted");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/get/{mngId}")
	@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
	public ResponseEntity<Object> getManager(@PathVariable int mngId) {
		try {
			Manager mgn = managerService.getManagerById(mngId);
			return ResponseEntity.ok(mgn);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> geAllManagers() {
		try {
			List<Manager> list = managerService.getAllManagers();
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/login")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Object> login(@RequestBody AuthRequest authRequest) {
		try {
			Manager manager = managerService.loginManager(authRequest.getUsername(), authRequest.getPassword());
			return ResponseEntity.ok(manager);
		}
		catch(Exception e) {
			return new ResponseEntity<>("Inavlid Username or Password.", HttpStatus.NOT_FOUND);
		}
	}
}

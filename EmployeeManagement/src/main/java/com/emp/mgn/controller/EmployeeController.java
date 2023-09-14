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

import com.emp.mgn.entity.Employee;
import com.emp.mgn.model.AuthRequest;
import com.emp.mgn.service.EmployeeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/add")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
		try {
			employeeService.saveEmployee(employee);
			return ResponseEntity.ok("Employee added");
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee) {
		try {
			employeeService.updateEmployee(employee);
			return ResponseEntity.ok("Employee updated");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{empId}")
	@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
	public ResponseEntity<Object> deleteEmployee(@PathVariable int empId) {
		try {
			employeeService.deleteEmployee(empId);
			return ResponseEntity.ok("Employee Deleted");
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/get/{empId}")
	@PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','ADMIN')")
	public ResponseEntity<Object> getEmployee(@PathVariable int empId) {
		try {
			Employee emp = employeeService.getEmployeeById(empId);
			return ResponseEntity.ok(emp);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
	public ResponseEntity<Object> geAllEmployees() {
		try {
			List<Employee> list = employeeService.getAllEmployees();
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/login")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<Object> login(@RequestBody AuthRequest authRequest) {
		try {
			Employee employee = employeeService.loginEmployee(authRequest.getUsername(), authRequest.getPassword());
			return ResponseEntity.ok(employee);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}

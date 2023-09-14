package com.emp.mgn.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.mgn.entity.Employee;
import com.emp.mgn.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	

	
	//add employee
	public Employee saveEmployee(Employee employee) throws Exception {
		Optional<Employee> optEmployee = employeeRepository.findByUsername(employee.getUsername());
		if(optEmployee.isPresent()) {
			throw new Exception("Username already exist");
		}
		return employeeRepository.save(employee);
	}
	
	//update employee
	public Employee updateEmployee(Employee employee) throws Exception {
		employeeRepository.findById(employee.getId()).orElseThrow(() -> new Exception("Employee Not Found with Id : " + employee.getId()));
		return employeeRepository.save(employee);
	}
	
	//delete employee
	public void deleteEmployee(int empId) throws Exception {
		employeeRepository.findById(empId).orElseThrow(() -> new Exception("Employee Not Found with Id : " + empId));
		employeeRepository.deleteById(empId);
	}
	
	//get employee by Id
	public Employee getEmployeeById(int empId) throws Exception {
		Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new Exception("Employee Not Found with Id : " + empId));
		return employee;
	}
	
	//get all employees
	public List<Employee> getAllEmployees() {
		List<Employee> list = employeeRepository.findAll();
		return list;
	}
	
	//login employee
	public Employee loginEmployee(String username, String password) throws Exception {
		Employee employee = employeeRepository.findByUsername(username).orElseThrow(() -> new Exception("Employee Not Found with username : " + username));
	
		if(!employee.getPassword().equals(password)) {
			throw new Exception("Password Invalid");
		}
		return employee;
	}
}

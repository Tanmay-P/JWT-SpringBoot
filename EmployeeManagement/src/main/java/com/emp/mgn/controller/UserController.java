package com.emp.mgn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.emp.mgn.entity.User;
import com.emp.mgn.model.AuthRequest;
import com.emp.mgn.service.UserService;
import com.emp.mgn.util.JwtUtil;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@GetMapping("/")
	@PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','ADMIN')")
	public String welcome() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "Welcome " + authentication.getName();
	}
	
	@PostMapping("/register")
	public String register(@RequestBody User user) {
		User registeredUser = service.registerUser(user);
		return registeredUser.getUsername() + " Registered Success";
	}
	
	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception{
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
					);
		}
		catch(Exception e) {
			throw new Exception("Invalid Username or Password.");
		}
		
		return jwtUtil.generateToken(authRequest.getUsername());
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> admin() {
		return ResponseEntity.ok("Welcome Admin");
	}
	
	@GetMapping("/manager")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<String> manager() {
		return ResponseEntity.ok("Welcome Manager");
	}
	
	@GetMapping("/employee")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public ResponseEntity<String> employee() {
		return ResponseEntity.ok("Welcome Employee");
	}
	
}

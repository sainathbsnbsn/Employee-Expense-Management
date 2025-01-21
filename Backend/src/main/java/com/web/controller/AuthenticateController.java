package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.dto.AuthRequest;
import com.web.dto.EmployeeDetailsDto;
import com.web.dto.JwtResponse;
import com.web.exception.EmployeeException;
import com.web.service.EmployeeDetailsService;
import com.web.service.JwtService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/expense")
public class AuthenticateController {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private EmployeeDetailsService userService;
	
	@Autowired
	Environment environment;
	
	@GetMapping("/test/{empid}")
	public ResponseEntity<EmployeeDetailsDto> getEmployeesById(@PathVariable String empid) throws EmployeeException
	{
		EmployeeDetailsDto one_emp = userService.getEmployeeById(empid);	
		return new ResponseEntity<>( one_emp, HttpStatus.OK);
	}
	
	
	@GetMapping("/welcome")
	@PreAuthorize("hasAuthority('Manager')")
	public String welcome() {
		return "welcome manager";
	}
	
	@GetMapping("/welcomeemployee")
	@PreAuthorize("hasAuthority('Employee')")
	public String welcome_employee() {
		return "welcome employee";
	}
	
	@PostMapping("/new")
	public ResponseEntity<EmployeeDetailsDto> addNewUser(@RequestBody EmployeeDetailsDto userInfo) throws EmployeeException {
		//System.out.println("in add new user");
		return new ResponseEntity<>(userService.addEmployee(userInfo), HttpStatus.OK);
	}
	
//	@DeleteMapping("/del/{empid}")
//	public ResponseEntity<String> delNewUser(@PathVariable Long empid) {
//		return new ResponseEntity<>(userService.delUser(empid), HttpStatus.OK);
//	}
	//http://localhost:8888/expense/authenticate
	@PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		//System.out.println(authRequest.getPassword());
		//System.out.println(authRequest.getUsername().getClass());
       // System.out.println(authentication.getCredentials());
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				System.out.println("in authenticate class");
			    //return jwtService.generateToken(String.valueOf(authRequest.getUsername()));
			    return ResponseEntity.ok(new JwtResponse(jwtService.generateToken(String.valueOf(authRequest.getUsername())),authRequest.getUsername(),authentication.getAuthorities().toArray()[0].toString()));
			} else {
			    throw new UsernameNotFoundException("invalid user request !");
			}
        }
	
	public static String getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    System.out.println(authentication.getAuthorities().toArray()[0]);
		    return currentUserName;
		}
		return null;
	}

}

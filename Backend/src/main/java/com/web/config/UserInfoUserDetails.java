package com.web.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.web.entity.EmployeeDetails;




public class UserInfoUserDetails implements UserDetails{
	private String employeeId;
	private String password;
	private List<GrantedAuthority> authorityList;
	
	
	public UserInfoUserDetails(EmployeeDetails userInfo) {
		//System.out.println("UsedrInfoUserDetails");
		this.employeeId = userInfo.getAssociateId();
		this.password = userInfo.getWorkerId();
		this.authorityList = Arrays.stream(userInfo.getDesignation().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	//@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.authorityList;
	}


	//@Override
	public String getEmployeeId() {
		return this.employeeId;
	}


	//@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return String.valueOf(getEmployeeId());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	

}

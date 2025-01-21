package com.web.service;

import java.util.Optional;

import com.web.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.web.config.UserInfoUserDetails;
import com.web.entity.EmployeeDetails;


@Component
public class UserInfoUserDetailsService implements UserInfoUserDetailsSer, UserDetailsService{

	@Autowired
	private EmployeeRepo empRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<EmployeeDetails> userInfo = empRepository.findByAssociateId(username);
				
			//	findByemployeeId(Long.parseLong(username));
		
		return userInfo.map(UserInfoUserDetails::new)
				.orElseThrow(()->new UsernameNotFoundException("user not found"+username));
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<EmployeeDetails> userInfo = empRepository.findByAssociateId(username);
//		
//		//	findByemployeeId(Long.parseLong(username));
//	
//	return userInfo.map(UserInfoUserDetails::new)
//			.orElseThrow(()->new UsernameNotFoundException("user not found"+username));
//		// TODO Auto-generated method stub
//		//return null;
//	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		return null;
//	}
}







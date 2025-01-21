package com.web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserInfoUserDetailsSer {

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
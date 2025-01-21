package com.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	String username;
	String token;
	String type="Bearer";
	String role;
	public JwtResponse(String token,String username,String role) {
		this.role=role;
		this.token=token;
		this.username=username;
	}

}

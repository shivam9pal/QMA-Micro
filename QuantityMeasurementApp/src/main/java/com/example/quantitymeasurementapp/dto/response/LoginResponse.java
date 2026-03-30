package com.example.quantitymeasurementapp.dto.response;

import lombok.Data;

@Data

public class LoginResponse {

	String Jwt;
	Long id;
	public String getJwt() {
		return Jwt;
	}
	public void setJwt(String jwt) {
		Jwt = jwt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public LoginResponse(String jwt, Long id) {
		
		Jwt = jwt;
		this.id = id;
	}
	
	
}

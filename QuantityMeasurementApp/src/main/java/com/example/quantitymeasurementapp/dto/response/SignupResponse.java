package com.example.quantitymeasurementapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class SignupResponse {

    
	private Long id;
    private String username;
    
    public SignupResponse(Long id2, String username2) {
		id=id2;
		username=username2;
	}
}

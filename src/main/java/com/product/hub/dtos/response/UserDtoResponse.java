package com.product.hub.dtos.response;

import java.io.Serializable;

import com.product.hub.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse implements Serializable {
	
	private String userName;
	private Roles role;
	private String mobile;
	private String email;
	

}

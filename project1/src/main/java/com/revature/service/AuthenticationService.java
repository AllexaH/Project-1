package com.revature.service;

import com.revature.exceptions.UnauthorizedException;
import com.revature.model.User;

public class AuthenticationService {
	public void authorizeEmployeeAndManager(User user) throws UnauthorizedException {
		if(!(user.getUserRole().equals("employee") || user.getUserRole().equals("manager"))) {
			throw new UnauthorizedException("You must have an employee or manager role to access this resource");
		}
	}

	public void authorizeManager(User user) throws UnauthorizedException {
		if(user == null || !user.getUserRole().equals("manager")) {
			throw new UnauthorizedException("You must have a manager role to access this resource");
		}
	}

	public void authorizeEmployee(User user) throws UnauthorizedException {
		if(user == null || !user.getUserRole().equals("employee")) {
			throw new UnauthorizedException("You must have an employee role to access this resource");
		}
	}
}

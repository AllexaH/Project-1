package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.LoginDTO;
import com.revature.dto.MessageDTO;
import com.revature.model.User;
import com.revature.service.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AuthenticationController implements Controller {
	
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	public AuthenticationController() {
		this.userService = new UserService();
	}
	
	private Handler login = (ctx) -> {
		LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);
		
		User user = this.userService.getUserByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
		
		logger.info("AuthenticationController layer: CurrentUser: {}", user);
		
		HttpServletRequest req = ctx.req;
		
		HttpSession session = req.getSession();
		session.setAttribute("currentuser", user);
		
		ctx.json(user);
	};
	
	private Handler logout = (ctx) -> {
		HttpServletRequest req = ctx.req;
		
		req.getSession().invalidate();
			
	};
	
	private Handler checkIfLoggedIn = (ctx) -> {
		HttpSession session = ctx.req.getSession();
		
		logger.info("AuthenticationController layer: checkIfLoggedIn: {}", session.getAttribute("currentuser"));
		
		if(!(session.getAttribute("currentuser") == null)) {
			ctx.json(session.getAttribute("currentuser"));
			ctx.status(200);
		}
		else {
			ctx.json(new MessageDTO("User is not logged in"));
			ctx.status(401);
		}
	};
	
	@Override
	public void mapEndPoints(Javalin app) {
		app.post("/login", login);
		app.post("/logout", logout);
		app.get("/checkloginstatus", checkIfLoggedIn);
	}
	
}

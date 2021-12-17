package com.revature.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.AuthenticationController;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionMapper;
import com.revature.controller.ReimbursementController;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class Application {
	public static void main(String args[]) {
		Javalin app = Javalin.create((config) -> {
			config.enableCorsForAllOrigins();
			
			config.addStaticFiles("static", Location.CLASSPATH);
		});
		
		Logger logger = LoggerFactory.getLogger(Application.class);
		
		app.before(ctx -> {
			logger.info(ctx.method() + " request received to the " + ctx.path() + " endpoint");
		});
		
		mapControllers(app, new AuthenticationController(), new ReimbursementController());
		
		ExceptionMapper mapper = new ExceptionMapper();
		mapper.mapExceptions(app);
		
		app.start(8080);
	}
	
	public static void mapControllers(Javalin app, Controller...controllers) {
		for(int i = 0; i < controllers.length; i++) {
			controllers[i].mapEndPoints(app);
		}
	}
}

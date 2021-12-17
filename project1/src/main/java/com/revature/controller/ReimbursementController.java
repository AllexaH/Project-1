package com.revature.controller;

import java.io.InputStream;
import java.util.List;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.MessageDTO;
import com.revature.dto.UpdateStatusDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.AuthenticationService;
import com.revature.service.ReimbursementService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;

public class ReimbursementController implements Controller {
	
	private AuthenticationService authenService;
	private ReimbursementService reimbursementService;
	
	private Logger logger = LoggerFactory.getLogger(ReimbursementController.class);
	
	public ReimbursementController() {
		this.reimbursementService = new ReimbursementService();
		this.authenService = new AuthenticationService();
	}
	
	private Handler addReimbursements = (ctx) -> {
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authenService.authorizeEmployeeAndManager(currentlyLoggedInUser);
		
		String amount = ctx.formParam("amount");
		String reimbursementType = ctx.formParam("reimbursement_type");
		String description = ctx.formParam("description");
		
		UploadedFile file = ctx.uploadedFile("receipt");
		
		if(file == null) {
			ctx.status(400);
			ctx.json(new MessageDTO("Must have an image to upload"));
			return;
		}
		
		InputStream content = file.getContent();
		
		Tika tika = new Tika();
		
		String mimeType = tika.detect(content);
		
		Reimbursement addedReimbursement = this.reimbursementService.addReimbursement(currentlyLoggedInUser, amount, reimbursementType, description, mimeType, content);
		ctx.json(addedReimbursement);
		ctx.status();
	};
	
	private Handler getReimbursements = (ctx) -> {
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authenService.authorizeEmployeeAndManager(currentlyLoggedInUser);
		
		logger.info("ReimbursementController layer: CurrentUser {}", currentlyLoggedInUser);
		
		List<Reimbursement> reimbursements = this.reimbursementService.getReimbursements(currentlyLoggedInUser);
		
		ctx.json(reimbursements);
	};
	
	private Handler updateStatus = (ctx) -> {
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authenService.authorizeEmployeeAndManager(currentlyLoggedInUser);
		
		String reimbursementID = ctx.pathParam("id");
		UpdateStatusDTO dto = ctx.bodyAsClass(UpdateStatusDTO.class);
		
		Reimbursement reimbursements = this.reimbursementService.updateStatus(currentlyLoggedInUser, reimbursementID, dto.getStatus());
		
		ctx.json(reimbursements);
	};
	
	private Handler getReimbursementsByStatus = (ctx) -> {
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authenService.authorizeEmployeeAndManager(currentlyLoggedInUser);
		
		String status = ctx.pathParam("status");
		
		logger.info("ReimbursementController layer: CurrentUser {}", currentlyLoggedInUser);
		
		List<Reimbursement> reimbursements = this.reimbursementService.getReimbursementsByStatus(currentlyLoggedInUser, status);
		
		ctx.json(reimbursements);
	};
	
	private Handler getImageFromReimbursementByID = (ctx) -> {
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authenService.authorizeEmployeeAndManager(currentlyLoggedInUser);
		
		String reimbursementID = ctx.pathParam("id");
		
		InputStream receipt = this.reimbursementService.getImageFromReimbursementByID(currentlyLoggedInUser, reimbursementID);
		
		Tika tika = new Tika();
		String mimeType = tika.detect(receipt);
		
		ctx.contentType(mimeType); 
		ctx.result(receipt);
	};

	@Override
	public void mapEndPoints(Javalin app) {
		app.put("/reimbursements", addReimbursements);
		app.get("/reimbursements", getReimbursements);
		app.patch("/reimbursements/{id}/status", updateStatus);
		app.get("/reimbursements/{status}/status", getReimbursementsByStatus);
		app.get("/reimbursements/{id}/image", getImageFromReimbursementByID);
	}
}

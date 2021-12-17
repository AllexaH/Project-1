package com.revature.service;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.ReimbursementDAO;
import com.revature.exceptions.ReimbursementAlreadyUpdatedException;
import com.revature.exceptions.ReimbursementImageNotFoundException;
import com.revature.exceptions.ReimbursementNotFoundException;
import com.revature.exceptions.UnauthorizedException;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public class ReimbursementService {
	
	private ReimbursementDAO reimbursementDao;
	
	private Logger logger = LoggerFactory.getLogger(ReimbursementService.class);
	
	public ReimbursementService() {
		this.reimbursementDao = new ReimbursementDAO();
	}

	public List<Reimbursement> getReimbursements(User currentlyLoggedInUser) throws SQLException {
		List<Reimbursement> reimbursements = null;
		
		if(currentlyLoggedInUser.getUserRole().equals("manager")) {
			reimbursements = this.reimbursementDao.getAllReimbursements();
		}
		else if(currentlyLoggedInUser.getUserRole().equals("employee")) {
			reimbursements = this.reimbursementDao.getAllReimbursementsByEmployee(currentlyLoggedInUser.getId());
		}
		
		logger.info("ReimbursementService layer: CurrentUser {}", currentlyLoggedInUser);
		
		return reimbursements;
	}

	public Reimbursement addReimbursement(User currentlyLoggedInUser, String amount1, String reimbursementType,
			String description, String mimeType, InputStream content) throws SQLException {
		Set<String> allowedFileTypes = new HashSet<>();
		allowedFileTypes.add("image/jpeg");
		allowedFileTypes.add("image/png");
		allowedFileTypes.add("image/gif");
		
		if(!allowedFileTypes.contains(mimeType)) {
			throw new InvalidParameterException("When adding a receipt image, only PNG, JPEG, or GIF are allowed");
		}
		
		int employeeID = currentlyLoggedInUser.getId();
		
		int amount = Integer.parseInt(amount1);
		
		Set<String>allowedReimbursementTypes = new HashSet<>();
		allowedReimbursementTypes.add("TRAVEL");
		allowedReimbursementTypes.add("FOOD");
		allowedReimbursementTypes.add("LODGING");
		allowedReimbursementTypes.add("OTHER");
		
		if(!allowedReimbursementTypes.contains(reimbursementType)) {
			throw new InvalidParameterException("When stating a reimursement type, only TRAVEL, FOOD, LODGING, or OTHER are allowed");
		}
		
		Reimbursement addedReimbursement = this.reimbursementDao.addReimbursement(employeeID, amount, reimbursementType,
				description, content);
		
		return addedReimbursement;
	}

	public Reimbursement updateStatus(User currentlyLoggedInUser, String reimbursementID, String status) throws SQLException, ReimbursementNotFoundException, ReimbursementAlreadyUpdatedException {
		try {
			int id = Integer.parseInt(reimbursementID);
			
			Reimbursement reimbursement = this.reimbursementDao.getReimbursementById(id);
			
			if(reimbursement == null) {
				throw new ReimbursementNotFoundException("Reimbursement with id " + reimbursementID + " was not found");
			}
			
			logger.info("ReimbursementService layer: Status {}", reimbursement.getStatus());
			String temp = reimbursement.getStatus();
			//String temp = "pending";
			
			if(reimbursement.getStatus() == temp) {
				logger.info("ReimbursementService layer: Status {}", reimbursement.getStatus());
				this.reimbursementDao.updateStatus(id, status, currentlyLoggedInUser.getId());
			}
			else {
				throw new ReimbursementAlreadyUpdatedException("Reimbursement has already been updates, so we cannot change the status"
						+ " to the reimbursement");
			}
			
			return this.reimbursementDao.getReimbursementById(id);
		}
		catch(NumberFormatException e) {
			throw new InvalidParameterException("Assigment id supplied must be an int");
		}
	}

	public List<Reimbursement> getReimbursementsByStatus(User currentlyLoggedInUser, String status) throws SQLException, UnauthorizedException {
		List<Reimbursement> reimbursements = null;
		
		if(currentlyLoggedInUser.getUserRole().equals("manager")) {
			reimbursements = this.reimbursementDao.getAllReimbursementsByStatus(status);
		}
		else {
			throw new UnauthorizedException("Must be a manager to update the status of a reimbursement");
		}
		
		return reimbursements;
	}

	public InputStream getImageFromReimbursementByID(User currentlyLoggedInUser, String reimbursementID) throws ReimbursementImageNotFoundException, SQLException, UnauthorizedException {
		try {
			int id = Integer.parseInt(reimbursementID);
			
			if (currentlyLoggedInUser.getUserRole().equals("employee")) {
				int employeeId = currentlyLoggedInUser.getId();
				List<Reimbursement> reimbursementsThatBelongToEmployee = this.reimbursementDao.getAllReimbursementsByEmployee(employeeId);
				
				Set<Integer> reimbursementIdsEncountered = new HashSet<>();
				for (Reimbursement a : reimbursementsThatBelongToEmployee) {
					reimbursementIdsEncountered.add(a.getId());
				}
				
				if (!reimbursementIdsEncountered.contains(id)) {
					throw new UnauthorizedException("You cannot access the images of reimbursements that do not belong to yourself");
				}
			}
			
			InputStream receipt = this.reimbursementDao.getImageFromReimbursementById(id);
			
			if (receipt == null) {
				throw new ReimbursementImageNotFoundException("Receipt was not found for reimbursement id: " + id);
			}
			
			return receipt;
			
		} catch(NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}
		
	}

}

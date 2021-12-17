package com.revature.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.ReimbursementDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.UnauthorizedException;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.AuthenticationService;

public class Testing {
	
	@Test
	public void testLogin() throws SQLException {
		UserDAO mockUserDao = mock(UserDAO.class);
		User user1 = new User(1, "JaneD", "password1", "Jane", "Doe", "janed@gmail.com", "manager");
		
		when(mockUserDao.getUserByUsernameAndPassword(eq("JaneD"), eq("password1"))).thenReturn(user1);
		
		User actual = mockUserDao.getUserByUsernameAndPassword("JaneD", "password1");
		User expected = new User(1, "JaneD", "password1", "Jane", "Doe", "janed@gmail.com", "manager");
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testAuthorizeEmployeeAndManagerAsManagerPositive() throws SQLException, UnauthorizedException {
		User user1 = new User(1, "JaneD", "password1", "Jane", "Doe", "janed@gmail.com", "manager");
		
		AuthenticationService authenService = new AuthenticationService();
		
		authenService.authorizeEmployeeAndManager(user1);
	}
	
	@Test
	public void testAuthorizeEmployeeAndManagerAsEmployeePositive() throws SQLException, UnauthorizedException {
		User user1 = new User(1, "JaneD", "password1", "Jane", "Doe", "janed@gmail.com", "employee");
		
		AuthenticationService authenService = new AuthenticationService();
		
		authenService.authorizeEmployeeAndManager(user1);
	}
	
	@Test
	public void testAuthorizeEmployeeAsEmployeePositive() throws SQLException, UnauthorizedException {
		User user1 = new User(1, "JaneD", "password1", "Jane", "Doe", "janed@gmail.com", "employee");
		
		AuthenticationService authenService = new AuthenticationService();
		
		authenService.authorizeEmployee(user1);
	}
	
//	@Test
//	public void testAuthorizeEmployeeAsManagerNegative() throws SQLException, UnauthorizedException {
//		User user1 = new User(1, "JaneD", "password1", "Jane", "Doe", "janed@gmail.com", "manager");
//		
//		AuthenticationService authenService = new AuthenticationService();
//		
//		authenService.authorizeEmployee(user1);
//	}
	
	@Test
	public void testAuthorizeManagerAsManagerPositive() throws SQLException, UnauthorizedException {
		User user1 = new User(1, "JaneD", "password1", "Jane", "Doe", "janed@gmail.com", "manager");
		
		AuthenticationService authenService = new AuthenticationService();
		
		authenService.authorizeManager(user1);
	}
	
//	@Test
//	public void testAuthorizeManagerAsEmployeeNegative() throws SQLException, UnauthorizedException {
//		User user1 = new User(1, "JaneD", "password1", "Jane", "Doe", "janed@gmail.com", "employee");
//		
//		AuthenticationService authenService = new AuthenticationService();
//		
//		authenService.authorizeEmployee(user1);
//	}
	
	@Test
	public void testGetAllReimbursements() throws SQLException {
		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);
		Reimbursement reim1 = new Reimbursement(1, 100, "timeStamp1", "", "pending", "LODGING", "Hotel expenses", 0, 2);
		Reimbursement reim2 = new Reimbursement(2, 432, "timeStamp1", "", "pending", "TRAVEL", "Flight expenses", 0, 2);
		
		List<Reimbursement> reimbursementsFromDao = new ArrayList<>();
		reimbursementsFromDao.add(reim1);
		reimbursementsFromDao.add(reim2);
		
		when(mockReimbursementDao.getAllReimbursements()).thenReturn(reimbursementsFromDao);
		
		List<Reimbursement> actual = mockReimbursementDao.getAllReimbursements();
		List<Reimbursement> expected = new ArrayList<>();
		expected.add(new Reimbursement(1, 100, "timeStamp1", "", "pending", "LODGING", "Hotel expenses", 0, 2));
		expected.add(new Reimbursement(2, 432, "timeStamp1", "", "pending", "TRAVEL", "Flight expenses", 0, 2));
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAllReimbursementsByEmployee1() throws SQLException {
		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);
		Reimbursement reim1 = new Reimbursement(1, 100, "timeStamp1", "", "pending", "LODGING", "Hotel expenses", 0, 1);
		Reimbursement reim2 = new Reimbursement(2, 432, "timeStamp1", "", "pending", "TRAVEL", "Flight expenses", 0, 1);
		Reimbursement reim3 = new Reimbursement(3, 100, "timeStamp1", "", "pending", "LODGING", "Hotel expenses", 0, 2);
		Reimbursement reim4 = new Reimbursement(4, 432, "timeStamp1", "", "pending", "TRAVEL", "Flight expenses", 0, 2);
		
		List<Reimbursement> reimbursementsFromDao1 = new ArrayList<>();
		reimbursementsFromDao1.add(reim1);
		reimbursementsFromDao1.add(reim2);
		
		List<Reimbursement> reimbursementsFromDao2 = new ArrayList<>();
		reimbursementsFromDao2.add(reim3);
		reimbursementsFromDao2.add(reim4);
		
		when(mockReimbursementDao.getAllReimbursementsByEmployee(eq(1))).thenReturn(reimbursementsFromDao1);
		
		List<Reimbursement> actual = mockReimbursementDao.getAllReimbursementsByEmployee(1);
		List<Reimbursement> expected = new ArrayList<>();
		expected.add(new Reimbursement(1, 100, "timeStamp1", "", "pending", "LODGING", "Hotel expenses", 0, 1));
		expected.add(new Reimbursement(2, 432, "timeStamp1", "", "pending", "TRAVEL", "Flight expenses", 0, 1));
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAllReimbursementsByEmployee2() throws SQLException {
		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);
		Reimbursement reim1 = new Reimbursement(1, 100, "timeStamp1", "", "pending", "LODGING", "Hotel expenses", 0, 1);
		Reimbursement reim2 = new Reimbursement(2, 432, "timeStamp1", "", "pending", "TRAVEL", "Flight expenses", 0, 1);
		Reimbursement reim3 = new Reimbursement(3, 100, "timeStamp1", "", "pending", "LODGING", "Hotel expenses", 0, 2);
		Reimbursement reim4 = new Reimbursement(4, 432, "timeStamp1", "", "pending", "TRAVEL", "Flight expenses", 0, 2);
		
		List<Reimbursement> reimbursementsFromDao1 = new ArrayList<>();
		reimbursementsFromDao1.add(reim1);
		reimbursementsFromDao1.add(reim2);
		
		List<Reimbursement> reimbursementsFromDao2 = new ArrayList<>();
		reimbursementsFromDao2.add(reim3);
		reimbursementsFromDao2.add(reim4);
		
		when(mockReimbursementDao.getAllReimbursementsByEmployee(eq(2))).thenReturn(reimbursementsFromDao2);
		
		List<Reimbursement> actual = mockReimbursementDao.getAllReimbursementsByEmployee(2);
		List<Reimbursement> expected = new ArrayList<>();
		expected.add(new Reimbursement(3, 100, "timeStamp1", "", "pending", "LODGING", "Hotel expenses", 0, 2));
		expected.add(new Reimbursement(4, 432, "timeStamp1", "", "pending", "TRAVEL", "Flight expenses", 0, 2));
		
		Assertions.assertEquals(expected, actual);
	}
	
//	@Test 
//	public void testAddReimbursement() {
//		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);
//		
//		Date date = new Date();
//		Timestamp timestamp2 = new Timestamp(date.getTime());
//		
//		Reimbursement reim1 = new Reimbursement(1, 50, timestamp2, null, "pending", "TRAVEL", "Plane", 0, 1);
//		
//		when(mockReimbursementDao.addReimbursement(eq(1), eq(50), eq("TRAVEL"), eq("Plane"), eq(null))).thenReturn(reim1);
//	}
//	
}

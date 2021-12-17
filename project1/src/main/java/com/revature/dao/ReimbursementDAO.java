package com.revature.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.model.Reimbursement;
import com.revature.utiliy.JDBCUtility;

public class ReimbursementDAO {
	
	private Logger logger = LoggerFactory.getLogger(ReimbursementDAO.class);

	public List<Reimbursement> getAllReimbursements() throws SQLException {
		logger.info("getAllReimbursements in DAO layer invoked");
		
		try(Connection con = JDBCUtility.getConnection()) {
			List<Reimbursement> reimbursements = new ArrayList<>();
			
			String sql = "SELECT * FROM reimbursement_p1.reimbursements";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int reimbursementID = rs.getInt("id");
				int amount = rs.getInt("amount");
				String submittedTimeStamp = rs.getString("submitted_time_stamp");
				String resolvedTimeStamp = rs.getString("resolved_time_stamp");
				String status = rs.getString("status");
				String type = rs.getString("reimbursement_type");
				String description = rs.getString("description");
				int managerID = rs.getInt("manager_id");
				int employeeID = rs.getInt("employee_id");
				
				Reimbursement reimbursement = new Reimbursement(reimbursementID, amount, submittedTimeStamp, 
						resolvedTimeStamp, status, type, description, managerID, employeeID);
				
				reimbursements.add(reimbursement);
			}
			
			return reimbursements;
		}
	}

	public List<Reimbursement> getAllReimbursementsByEmployee(int employeeID) throws SQLException {
		logger.info("getAllReimbursements in DAO layer invoked");
		
		try(Connection con = JDBCUtility.getConnection()) {
			List<Reimbursement> reimbursements = new ArrayList<>();
			
			String sql = "SELECT * FROM reimbursement_p1.reimbursements WHERE employee_id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, employeeID);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int reimbursementID = rs.getInt("id");
				int amount = rs.getInt("amount");
				String submittedTimeStamp = rs.getString("submitted_time_stamp");
				String resolvedTimeStamp = rs.getString("resolved_time_stamp");
				String status = rs.getString("status");
				String type = rs.getString("reimbursement_type");
				String description = rs.getString("description");
				int managerID = rs.getInt("manager_id");
				int employeeId = rs.getInt("employee_id");
				
				Reimbursement reimbursement = new Reimbursement(reimbursementID, amount, submittedTimeStamp, 
						resolvedTimeStamp, status, type, description, managerID, employeeId);
				
				reimbursements.add(reimbursement);
			}
			
			return reimbursements;
		}
	}

	public Reimbursement addReimbursement(int employeeID, int amount, String reimbursementType, String description,
			InputStream receipt) throws SQLException {
		try(Connection con = JDBCUtility.getConnection()) {
			con.setAutoCommit(false);
			
			String sql = "INSERT INTO reimbursement_p1.reimbursements (amount, submitted_time_stamp, status, reimbursement_type, description, receipt, employee_id)"
					+ " VALUES (?, now(), ?, ?, ?, ?, ?);";
			
			String submittedTimeStamp = "temp";
			String status = "pending";
			
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, amount);
			pstmt.setString(2, status);
			pstmt.setString(3, reimbursementType);
			pstmt.setString(4, description);
			pstmt.setBinaryStream(5, receipt);
			pstmt.setInt(6, employeeID);
			
			int numberOfInsertedRecords = pstmt.executeUpdate();
			
			if(numberOfInsertedRecords != 1) {
				throw new SQLException("Issue occurres when adding reimbursement");
			}
			
			ResultSet rs = pstmt.getGeneratedKeys();
			
			rs.next();
			int generatedID = rs.getInt(1);
			
			con.commit();
			
			return new Reimbursement(generatedID, amount, submittedTimeStamp, null, status, reimbursementType, description, 0, employeeID);
		}
	}

	public Reimbursement getReimbursementById(int id) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			
			String sql = "SELECT * FROM reimbursement_p1.reimbursements WHERE id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int reimbursementID = rs.getInt("id");
				int amount = rs.getInt("amount");
				String submittedTimeStamp = rs.getString("submitted_time_stamp");
				String resolvedTimeStamp = rs.getString("resolved_time_stamp");
				String status = rs.getString("status");
				String type = rs.getString("reimbursement_type");
				String description = rs.getString("description");
				int managerID = rs.getInt("manager_id");
				int employeeId = rs.getInt("employee_id");
				
				return new Reimbursement(reimbursementID, amount, submittedTimeStamp, resolvedTimeStamp, status, type, description, managerID, employeeId);
			} else {
				return null;
			}
			
		}
	}

	public void updateStatus(int id, String status, int managerID) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE reimbursement_p1.reimbursements "
					+ "SET "
					+ "resolved_time_stamp = date_trunc('second',now()::timestamp), "
					+ "status = ?, "
					+ "manager_id = ? "
					+ "WHERE id = ?;";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, managerID);
			pstmt.setInt(3, id);
			
			int updatedCount = pstmt.executeUpdate();
			
			if (updatedCount != 1) {
				throw new SQLException("Something bad occurred when trying to update grade");
			}
		}
	}

	public List<Reimbursement> getAllReimbursementsByStatus(String status) throws SQLException {
		List<Reimbursement> reimbursements = new ArrayList<>();
		
		try (Connection con = JDBCUtility.getConnection()) {
			
			String sql = "SELECT * FROM reimbursement_p1.reimbursements WHERE status = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int reimbursementID = rs.getInt("id");
				int amount = rs.getInt("amount");
				String submittedTimeStamp = rs.getString("submitted_time_stamp");
				String resolvedTimeStamp = rs.getString("resolved_time_stamp");
				String cStatus = rs.getString("status");
				String type = rs.getString("reimbursement_type");
				String description = rs.getString("description");
				int managerID = rs.getInt("manager_id");
				int employeeId = rs.getInt("employee_id");
				
				Reimbursement reimbursement = new Reimbursement(reimbursementID, amount, submittedTimeStamp, 
						resolvedTimeStamp, status, type, description, managerID, employeeId);
				
				reimbursements.add(reimbursement);
			} 
			return reimbursements;
		}
	}

	public InputStream getImageFromReimbursementById(int id) throws SQLException {
		try(Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT receipt FROM reimbursement_p1.reimbursements WHERE id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				InputStream receipt = rs.getBinaryStream("receipt");
				
				return receipt;
			}
			
			return null;
		}
	}

}

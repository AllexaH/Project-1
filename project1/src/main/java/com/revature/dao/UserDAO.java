package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.model.User;
import com.revature.utiliy.JDBCUtility;

public class UserDAO {
	public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM reimbursement_p1.users WHERE username = ? AND password = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("id");
				String user = rs.getString("username");
				String pass = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				String userRole = rs.getString("user_role");
				
				return new User(id,user, pass, firstName, lastName, email, userRole);
			}
			else {
				return null;
			}
		}
	}
}

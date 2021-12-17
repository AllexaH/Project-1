package com.revature.model;

import java.io.InputStream;
import java.util.Objects;

public class Reimbursement {
	
	private int id;
	private int amount;
	private String submittedTimeStamp;
	private String resolvedTimeStamp;
	private String status;
	private String type;
	private String description;
	private int managerID;
	private int employeeID;
	
	public Reimbursement() {
		
	}

	public Reimbursement(int id, int amount, String submittedTimeStamp, String resolvedTimeStamp, String status,
			String type, String description, int managerID, int employeeID) {
		super();
		this.id = id;
		this.amount = amount;
		this.submittedTimeStamp = submittedTimeStamp;
		this.resolvedTimeStamp = resolvedTimeStamp;
		this.status = status;
		this.type = type;
		this.description = description;
		this.managerID = managerID;
		this.employeeID = employeeID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getSubmittedTimeStamp() {
		return submittedTimeStamp;
	}

	public void setSubmittedTimeStamp(String submittedTimeStamp) {
		this.submittedTimeStamp = submittedTimeStamp;
	}

	public String getResolvedTimeStamp() {
		return resolvedTimeStamp;
	}

	public void setResolvedTimeStamp(String resolvedTimeStamp) {
		this.resolvedTimeStamp = resolvedTimeStamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, description, employeeID, id, managerID, resolvedTimeStamp, status,
				submittedTimeStamp, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		return amount == other.amount && Objects.equals(description, other.description)
				&& employeeID == other.employeeID && id == other.id && managerID == other.managerID
				&& Objects.equals(resolvedTimeStamp, other.resolvedTimeStamp) && Objects.equals(status, other.status)
				&& Objects.equals(submittedTimeStamp, other.submittedTimeStamp) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submittedTimeStamp=" + submittedTimeStamp
				+ ", resolvedTimeStamp=" + resolvedTimeStamp + ", status=" + status + ", type=" + type
				+ ", description=" + description + ", managerID=" + managerID + ", employeeID=" + employeeID + "]";
	}

}

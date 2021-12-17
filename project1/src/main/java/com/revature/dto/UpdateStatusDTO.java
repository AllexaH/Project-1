package com.revature.dto;

import java.util.Objects;

public class UpdateStatusDTO {
	private String status;

	public UpdateStatusDTO() {
		super();
	}

	public UpdateStatusDTO(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateStatusDTO other = (UpdateStatusDTO) obj;
		return Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "UpdateStatusDTO [status=" + status + "]";
	}
	
}

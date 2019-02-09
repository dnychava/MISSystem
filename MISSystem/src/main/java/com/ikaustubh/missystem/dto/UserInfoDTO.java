package com.ikaustubh.missystem.dto;

import org.springframework.stereotype.Component;

@Component
public class UserInfoDTO {
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", password=" + password + "]";
	}
	
	
}

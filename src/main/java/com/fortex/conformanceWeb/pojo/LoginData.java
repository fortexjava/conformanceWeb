/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.pojo;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class LoginData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String password;
	private Integer role;
	private String email;
	private String lastLoginTime;
	private String serverGroups;
	private String webconsoleSession;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getServerGroups() {
		return serverGroups;
	}
	public void setServerGroups(String serverGroups) {
		this.serverGroups = serverGroups;
	}
	public String getWebconsoleSession() {
		return webconsoleSession;
	}
	public void setWebconsoleSession(String webconsoleSession) {
		this.webconsoleSession = webconsoleSession;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}

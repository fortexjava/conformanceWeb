/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.pojo;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class LogonResults implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String error;
	private LoginData data;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public LoginData getData() {
		return data;
	}
	public void setData(LoginData data) {
		this.data = data;
	}
	 	
}
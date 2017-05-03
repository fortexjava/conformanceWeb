/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author IvanFok
 *
 */
public class LoginForm {
	
	@NotNull
	@Size(min=1)
	private String loginId;
	
	@NotNull
	@Size(min=1)
	private String password;
	
	//private Integer loginType;
	
	private String illegal;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public Integer getLoginType() {
//		return loginType;
//	}
//
//	public void setLoginType(Integer loginType) {
//		this.loginType = loginType;
//	}
	
	
	
	public String getIllegal() {
		return illegal;
	}

	public void setIllegal(String illegal) {
		this.illegal = illegal;
	}

	public String toString() {
        return "";
    }
}

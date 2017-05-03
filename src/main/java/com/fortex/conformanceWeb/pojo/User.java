/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.pojo;

/**
 * @author Administrator
 *
 */
public class User {
	private String loginId;
	
	private Integer loginType;
	
	private String hashLoginId;
	
	private String hasLoginType;
	
	private String senderID;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public String getHashLoginId() {
		return hashLoginId;
	}

	public void setHashLoginId(String hashLoginId) {
		this.hashLoginId = hashLoginId;
	}

	public String getHasLoginType() {
		return hasLoginType;
	}

	public void setHasLoginType(String hasLoginType) {
		this.hasLoginType = hasLoginType;
	}

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}
	
	
}

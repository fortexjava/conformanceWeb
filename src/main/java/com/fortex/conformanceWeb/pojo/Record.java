/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.pojo;

/**
 * @author Administrator
 *
 */
public class Record extends BasePojo{
	
	private String userName;
	private int step;
	private int routeType;
	private String message;
	private int status;
	private String reason;
	private Integer tag;
	private String code;
	private String senderId;
	
	private Solution solution;
	 
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	 
	public int getRouteType() {
		return routeType;
	}
	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public Integer getTag() {
		return tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Solution getSolution() {
		return solution;
	}
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	 
	
	
}

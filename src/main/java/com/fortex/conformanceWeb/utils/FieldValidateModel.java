package com.fortex.conformanceWeb.utils;

import java.util.Map;

public class FieldValidateModel {
	private int number;
	private boolean isRequired;
	private String defaultVal;
	private Map<Integer, FieldValidateModel> groups;
	public FieldValidateModel(int number, boolean isRequired) {
		super();
		this.number = number;
		this.isRequired = isRequired;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public boolean isRequired() {
		return isRequired;
	}
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public Map<Integer, FieldValidateModel> getGroups() {
		return groups;
	}
	public void setGroups(Map<Integer, FieldValidateModel> groups) {
		this.groups = groups;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	
	@Override
	public String toString() {
		return "[number:" + number + ",isRequired:" + isRequired + ",groups:" + groups + "]";
	}
}

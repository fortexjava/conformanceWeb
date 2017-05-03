/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Administrator
 *
 */
public class SolutionForm {
	@NotNull
	private Integer tag;
	
	@NotNull
	@Size(min=1)
	private String code;
	
	private String solution;

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

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	
}

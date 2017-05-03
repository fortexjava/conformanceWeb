/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class ListServer {
	private String error;
	private List<ListServerData> data = new ArrayList<ListServerData>();
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<ListServerData> getData() {
		return data;
	}
	public void setData(List<ListServerData> data) {
		this.data = data;
	}		
}

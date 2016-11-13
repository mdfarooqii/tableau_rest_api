/**
 * 
 */
package com.ubs.risk.arisk.sampleTableau.restapi;

import java.util.List;

/**
 * @author faroooq
 *
 */
public class WorkBook {
	
	
	private String workBookName;
	private String workBookId;
	private String siteId;
	private List<View> views;
	
	public String getWorkBookName() {
		return workBookName;
	}
	public void setWorkBookName(String workBookName) {
		this.workBookName = workBookName;
	}
	public String getWorkBookId() {
		return workBookId;
	}
	public void setWorkBookId(String workBookId) {
		this.workBookId = workBookId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	
	
	

}

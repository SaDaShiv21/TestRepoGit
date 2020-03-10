package com.actiance.generics;

public class CSVdata {

	private String testCaseNo;
	private String fromuser;
	private String imOrGc;
	private String touserorroom;
	private String msgtxt;
	private String execute;
	
	private String sqlquery;
	private String checkdisc;
	private String checkjoinleave;
	
	public String getTestCaseNo() {
		return testCaseNo;
	}
	public void setTestCaseNo(String testCaseNo) {
		this.testCaseNo = testCaseNo;
	}
	//strs-----
	public void setFromUser(String frmUsr) {
		this.fromuser = frmUsr;
	}
	public void setImOrGc(String imorgc) {
		this.imOrGc = imorgc;
	}
	public void setToUserOrRoom(String to) {
		this.touserorroom = to;
	}
	public void setMsgTxt(String msgtxt) {
		this.msgtxt = msgtxt;
	}
	public void setExecute(String execute) {
		this.execute = execute;
	}
	
	public void setCheckDisclaimer(String checkdisc) {
		this.checkdisc = checkdisc;
	}
	public void setCheckJoinLeave(String checkjoinleave) {
		this.checkjoinleave = checkjoinleave;
	}
	public void setSqlQuery(String sqlquery) {
		this.sqlquery = sqlquery;
	}
	//gtrs-----
	public String getFromUser() {
		return fromuser;
	}
	public String getImOrGc() {
		return imOrGc;
	}
	public String getToUserOrRoom() {
		return touserorroom;
	}
	public String getMessageText() {
		return msgtxt;
	}
	public String getExecuteYesOrNO() {
		return execute;
	}
	public String geCheckDisclaimer() {
		return checkdisc;
	}
	public String getCheckJoinLeave() {
		return checkjoinleave ;
	}
	public String getSqlQuery() {
		return sqlquery;
	}
	
	public String toString(){
		//System.out.println("Ovrrdn-ToString-InsideCsVData");
		return this.getTestCaseNo();
	}

}

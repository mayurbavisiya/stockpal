package com.in.exception;

public class ErrorDetails {
	private String responseMsg;
	private String responseCode;
	private boolean success;

	public ErrorDetails(String responseCode, String responseMsg, String success) {
		super();
		this.responseMsg = responseMsg;
		this.responseCode = responseCode;
		// this.success = success;
		this.success = (ExceptionUtility.getCode(success).trim().equals("true")) ? true : false;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}

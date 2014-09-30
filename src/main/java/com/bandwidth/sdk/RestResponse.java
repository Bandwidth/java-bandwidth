/**
 * 
 */
package com.bandwidth.sdk;

/**
 * @author smitchell
 * 
 */
public class RestResponse {
	private String responseText;
	private boolean error;
	private String contentType;
	private String location;
	private int status;

	public RestResponse(String text, int status) {
		this.responseText = text;
		this.error = (status >= 400);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public String getResponseText() {
		return responseText;
	}

	public boolean isError() {
		return error;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isJson() {
		return (this.contentType.toLowerCase().contains("application/json"));
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(this.getClass().getName()).append(
				this.hashCode()).append("\n");

		buf.append("status:").append(status).append("\n");
		buf.append("error:").append(error).append("\n");
		buf.append("contentType:").append(contentType).append("\n");
		buf.append("location:").append(location).append("\n");
		buf.append("responseText:").append(responseText).append("\n");

		return buf.toString();
	}
}

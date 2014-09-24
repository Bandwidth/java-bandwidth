package com.bandwidth.sdk;

public class AppPlatformException extends Exception 
{
	public AppPlatformException()
	{		
	}
	
	public AppPlatformException(String message)
	{
		super(message);
	}
	
	public AppPlatformException(Throwable cause)
	{
		super(cause);
	}
	
	public AppPlatformException(String message, Throwable cause)
	{
		super(message, cause);
	}

}

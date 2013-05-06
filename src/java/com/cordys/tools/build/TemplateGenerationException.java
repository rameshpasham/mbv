package com.cordys.tools.build;

public class TemplateGenerationException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TemplateGenerationException(String message)
	{
		super(message);
	}
	
	public TemplateGenerationException(String message,Throwable t)
	{
		super(message,t);
	}
}

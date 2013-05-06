package com.cordys.tools.mb;

/**
 *	This class has extra members that are required to prepare the Violations report
 */
public class ReportViolation extends Violation
{
	private String linenum = "0";
	private String line;
	
	public ReportViolation(String priority, String violationtext, String linenum)
	{
		this.setPriority(priority);
		this.setDescription(violationtext);
		this.linenum = linenum;
	}
	
	public ReportViolation(Violation violation,String linenum) 
	{
		setPriority(violation.getPriority());
		setDescription(violation.getDescription());
		setSuggestion(violation.getSuggestion());
		setViolationType(violation.getType());
		this.linenum = linenum;
	}
	
	public void setLineContent(String linecontent)
	{
		this.line = linecontent;
	}
	
	public String getLinenum()
	{
		return this.linenum;
	}
	
	public String getLineContent()
	{
		return this.line;
	}
}

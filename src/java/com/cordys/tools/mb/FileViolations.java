package com.cordys.tools.mb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class maintains the list of violations in a file  
 */
public class FileViolations 
{
	private String filepath;
	private String fileType;
	private List<ReportViolation> violations = new ArrayList<ReportViolation>();
	
	public FileViolations(File file,String filetype) 
	{
		this.filepath = file.getAbsolutePath();
		this.fileType = filetype;
	}
	
	public void addViolation(Violation violation,String linenum,String line)
	{
		ReportViolation reportViolation = new ReportViolation(violation,linenum);
		reportViolation.setLineContent(line);
		this.violations.add(reportViolation);
	}
	
	public void addViolation(ReportViolation reportViolation)
	{
		this.violations.add(reportViolation);
	}
	
	public String getFilePath()
	{
		return this.filepath;
	}
	
	public String getFileType()
	{
		return this.fileType;
	}
	
	public List<ReportViolation> getViolations()
	{
		return this.violations;
	}
}

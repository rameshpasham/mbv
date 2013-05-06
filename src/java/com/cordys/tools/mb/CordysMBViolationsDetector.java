package com.cordys.tools.mb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cordys.build.utils.FileSystemUtils;

public class CordysMBViolationsDetector 
{
	private final static Pattern COMMENT_PATTERN = Pattern.compile("^\\s*//");

	private static final List<FileViolations> mbViolationsByFile = new ArrayList<FileViolations>();
	private WebFileFilter webFilter;
	private boolean showlines = false;
	private boolean ignoreMarker = false;
	private List<Violation> violationsList = null;
	
	public void setFilenameFilter(WebFileFilter fileFilter)
	{
		this.webFilter = fileFilter;
	}
	
	public void setShowLines(boolean show)
	{
		this.showlines = show;
	}
	
	public void setIgnoreMarker(boolean ignore)
	{
		ignoreMarker = ignore;
	}

	public void generateViolationsReport(String violationsFilePath, Set<File> files, String outputFilePath)
	        throws Exception
	{
		// Reading the violations from file
		violationsList = ViolationsList.getViolations(violationsFilePath);

		for( File file : files)
		{
			if(webFilter.accept(file)) this.findViolationsInFile(file);
		}

		ReportGenerator reportGenerator = new ReportGenerator();
		reportGenerator.generateReport(mbViolationsByFile, outputFilePath);
	}

	public void generateViolationsReport(String violationsFilePath, String srcFolderPath, String outputFilePath)
	        throws Exception
	{
		//Reading the violations from file
		violationsList = ViolationsList.getViolations(violationsFilePath);
		
		List<File> allFiles = FileSystemUtils.listFiles(new File(srcFolderPath), webFilter, true);
		
		for (File file : allFiles) 
		{
			this.findViolationsInFile(file);
		}

		ReportGenerator reportGenerator = new ReportGenerator();
		reportGenerator.generateReport(mbViolationsByFile, outputFilePath);
	}

private void findViolationsInFile(File file) throws IOException
{
	try(BufferedReader br = new BufferedReader(new FileReader(file)))
	{
		String fileType = FileTypeUtil.getFileType(file);
		FileViolations fileViolations = new FileViolations(file, fileType);
		String line = null;
		int linenum = 0;
		boolean docViolationPriority1 = false; //If no DOCTYPE is present in the file 
		boolean docViolationPriority3 = false; //If there is no HTML5 DOCTYPE "<!DOCTYPE HTML>" is present in the file
		while((line = br.readLine()) != null)
		{
			linenum++;
			//For finding DOCTYPE in line1
			if(linenum == 1 && ("htm".equals(fileType)||"html".equals(fileType)))
			{
				Violation matchViolation = ViolationsList.getViolation("MISSING DOCTYPE HTML AT STARTING");
				boolean checkFile = true;
				for(String exclusionList : matchViolation.getExclusionlist())
				{
					// Path of the exclusion list will be from web folder					
					exclusionList = "web\\".concat(exclusionList); // 'web' prepended to the path
					String exclusionList1 = "web2\\".concat(exclusionList); // CWS has library files under web2 folder. Prepending that too. 
					if((file.getName().indexOf(exclusionList) != -1) || (file.getName().indexOf(exclusionList1) != -1))
					{
						checkFile = false; 
						break;
					}
				}
				if(checkFile)
				{
					if(line.toUpperCase().indexOf("<!DOCTYPE HTML>") == 0)
					{
						continue; //IF Standard DOCTYPE is present in the first line no need to check for other violations
					}
					else if(line.toUpperCase().indexOf("<!DOCTYPE HTML") == 0)
					{	
						docViolationPriority3 = true; //IF loose DOCTYPE is present in the first line no need to check for other violations but adding prio3
						continue;  
					}
					else
					{
						docViolationPriority1 = true;
					}
				
				}
			}
			
			if(COMMENT_PATTERN.matcher(line).find()) continue;

			// Check the line for all possible violations
			List<ReportViolation> lineViolations = new LinkedList<ReportViolation>();
			violation: for(Violation violation : violationsList)
			{
				if(!violation.isApplicable(fileType)) continue;

				boolean violationFound = false;
				if(violation.getRegExpression() != null)
				{
					Pattern p = Pattern.compile(violation.getRegExpression());
					Matcher m = p.matcher(line);
					violationFound = m.find();
				}
				violationFound |= (violation.getText() != null && line.contains(violation.getText()));

				if(violationFound)
				{
					// is violation still applicable after exception rules?
					for(String regexException : violation.getRegexExceptions())
					{
						Pattern p2 = Pattern.compile(regexException);
						Matcher m2 = p2.matcher(line);
						if(m2.find()) continue violation;
					}
					for(String textException : violation.getTextExceptions())
					{
						if(line.contains(textException)) continue violation;
					}
					ReportViolation reportViolation = new ReportViolation(violation, linenum + "");
					if(showlines) reportViolation.setLineContent(line);
					lineViolations.add(reportViolation);
				}
			}

			int nrSuppress = 0;
			int pos = line.indexOf("NOMBV");
			if(pos > -1 && !ignoreMarker)
			{
				nrSuppress = 1;
				int open = line.indexOf("(", pos);
				int close = line.indexOf(")", pos);
				if(open > -1 && close > -1 && open == pos + 5 && open < close)
				{
					try
					{
						nrSuppress = Integer.parseInt(line.substring(open + 1, close));
					}
					catch(NumberFormatException e)
					{
						System.err.println("NOMBV parse exception in " + file + ":" + linenum);
						// ignore, handle it as a single NOMBV
					}
				}
			}

			if(nrSuppress != 0)
			{
				if(nrSuppress != lineViolations.size())
					lineViolations.add(new ReportViolation("2", "#violations != #NOMBV", linenum + ""));
				else
					lineViolations.clear();
			}
			
			if(line.indexOf("setPublic(")>=0 || line.indexOf("setBehavior(")>=0)
			{
				docViolationPriority1 = false;
				docViolationPriority3 = false;
			}
			for(ReportViolation violation : lineViolations)
			{
				fileViolations.addViolation(violation);
			}
		}
		
		if(docViolationPriority1)
		{
			addingDocTypeViolation("MISSING DOCTYPE HTML AT STARTING", line, fileViolations);
			
		}
		else if(docViolationPriority3)
		{
			addingDocTypeViolation("STANDARD HTML5 DOCTYPE MISSING AT STARTING", line, fileViolations);
		}
		
		if(!fileViolations.getViolations().isEmpty())
		{
			mbViolationsByFile.add(fileViolations);
		}
	}
}
	
	//This will add the DOCTYPE violation
	private void addingDocTypeViolation(String violation, String line, FileViolations fileViolations)
	{
		Violation matchViolation = ViolationsList.getViolation(violation);
		ReportViolation reportViolationDOC = new ReportViolation(matchViolation, "1");
		if(showlines) reportViolationDOC.setLineContent(line);
		fileViolations.addViolation(reportViolationDOC);
	}
	
	public static void main(String[] args) throws Exception
	{
		if( args.length < 3 )
		{
			System.out.println("Usage: java com.cordys.tools.mb.CordysMBViolationsDetector <violations-input.xml> <input folder path to search web files> <output file path> [--lines]");
			System.out.println();
			System.out.println("--lines  : shows the violated lines content in report xml");
			return;
		}
		System.out.println("started...");
		CordysMBViolationsDetector mbDetector = new CordysMBViolationsDetector();
		if( args.length == 4)
		{
			String option = args[3];
			if( "--lines".equalsIgnoreCase(option) )
				mbDetector.setShowLines(true);
		}
		WebFileFilter fileFilter = new WebFileFilter();
		mbDetector.setFilenameFilter(fileFilter);
		mbDetector.generateViolationsReport(args[0],args[1],args[2]);
		System.out.println("done.");
	}
}

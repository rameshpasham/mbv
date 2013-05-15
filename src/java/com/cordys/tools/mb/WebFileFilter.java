package com.cordys.tools.mb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WebFileFilter
{
	private static final List<String> supportedFileTypes = new ArrayList<String>();
	private static final List<String> excludedFileNamePatterns = new ArrayList<String>();
	static
	{
		// default supported file types
		supportedFileTypes.add("htm");
		supportedFileTypes.add("html");
		supportedFileTypes.add("css");
		supportedFileTypes.add("caf");
		supportedFileTypes.add("js");
		supportedFileTypes.add("cws");
		
		//default excluded file path patterns
		excludedFileNamePatterns.add("/documentation/");
		excludedFileNamePatterns.add("/docs/");
		excludedFileNamePatterns.add("/test/");
		//excludedFileNamePatterns.add("_q.htm");
	}
	
	public boolean accept(File file)
	{
		String fileType = FileTypeUtil.getFileType(file);
		if( supportedFileTypes.contains(fileType) && !file.getName().endsWith("_q.htm") )
			return true;
		else
			return false;
	}

	public boolean accept(File dir, File file) 
	{
		boolean b1 = accept(file);
		return b1 && !isExcluded(dir.getAbsolutePath() + File.separator + file.getName());
	}
	
	private boolean isExcluded(String filePath) 
	{
		boolean exclude = false;
		filePath = filePath.replaceAll("\\\\", "/");
		for (String exludedPattern : excludedFileNamePatterns)
		{
			if( filePath.indexOf(exludedPattern) >= 0)
			{
				exclude = true;
				break;
			}
		}
		return exclude;
	}
	
	public static void addFileType(String fileType)
	{
		supportedFileTypes.add(fileType);
	}
	
	public static void addExcludedFileNamePattern(String filenamePattern)
	{
		excludedFileNamePatterns.add(filenamePattern);
	}
}

package com.cordys.tools.mb;

import java.io.File;

public class FileTypeUtil 
{
	/**
	 * returns type of the file based on the file extension
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileType(File file)
	{
		int dotpos = file.getName().lastIndexOf('.');
		if( dotpos == -1 )
			return "";
		else
		{
			return file.getName().substring(dotpos+1);
		}
	}
}

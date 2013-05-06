package com.cordys.build.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import com.cordys.tools.mb.WebFileFilter;


public class FileSystemUtils
{
	
	/**
	 * Gives the list of files in a particular directory.
	 * 
	 * @param directory -- Absolute path of the directory
	 * @param filter	-- searches on the specified filter type.If you don't want to pass any filter just pass null. 
	 * @param recurse   -- If it is true, it will search recursively through all the directories, if it is false it searches in the given level
	 * @return
	 */
	public static File[] listFilesAsArray(File directory, WebFileFilter filter, boolean recurse)
	{
		
		Collection<File> files = listFiles(directory,filter, recurse);
		
		File[] arr = new File[files.size()];
		
		return files.toArray(arr);
		
	}

	
	/**
	 * Lists the files in an Vector
	 * @param directory
	 * @param webFilter
	 * @param recurse
	 * @return
	 */
	public static List<File> listFiles(File directory,WebFileFilter webFilter,boolean recurse)
	{
	
		// List of files / directories
		List<File> files = new ArrayList<File>();
		
		// Get files / directories in the directory
		File[] entries = directory.listFiles();
		
		if ( entries == null )
			return files;
		
		// Go over entries
		for (File entry : entries)
		{
            // If there is no filter or the filter accepts the 
            // file / directory, add it to the list
            if (webFilter == null || webFilter.accept(directory, entry) )
            {
            	if ( !entry.isDirectory() && !entry.getName().startsWith(".") )
                    files.add(entry);
            }
            // If the file is a directory and the recurse flag
            // is set, recurse into the directory
            if (recurse && entry.isDirectory())
            {
            	if ( !entry.getName().startsWith(".") )
                    files.addAll(listFiles(entry, webFilter, recurse));
            }
		}
		
		// Return collection of files
		return files;                    
	}
	
	/**
	 * writes the document element to xml file
	 * 
	 * @param element
	 * @param filepath
	 * @throws IOException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 */
	public static void writeXmlFile( Element element, String filepath) throws IOException, TransformerFactoryConfigurationError, TransformerException  {

		// Prepare the DOM document for writing
		Source source = new DOMSource(element);

		// Prepare the output file
		File file = new File(filepath);
		if( !file.exists() )
			file.createNewFile();
		//doubt give to developer the output folder location not file name... ***
		   
		
		Result result = new StreamResult(file);

		// Write the DOM document to the file
		Transformer xformer = TransformerFactory.newInstance()
				.newTransformer();
		xformer.transform(source, result);
	}	
}




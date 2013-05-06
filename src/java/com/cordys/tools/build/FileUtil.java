package com.cordys.tools.build;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

public class FileUtil 
{

	public static File[] listFilesOnLastModifiedDate(File folderPath,FilenameFilter filenameFilter)
	{
		File[] files;
		if( filenameFilter == null )
			files = folderPath.listFiles();
		else
			files = folderPath.listFiles(filenameFilter);
		Arrays.sort( files, new Comparator<File>()
		{
			public int compare(File f1, File f2) {
				return new Long(f1.lastModified()).compareTo(f2.lastModified());
			}

		});
		
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
		
		Result result = new StreamResult(file);

		// Write the DOM document to the file
		Transformer xformer = TransformerFactory.newInstance()
				.newTransformer();
		xformer.transform(source, result);
	}	
}

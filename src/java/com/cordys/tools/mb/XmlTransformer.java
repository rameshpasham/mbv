package com.cordys.tools.mb;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XmlTransformer {

	public static void transform(File xmlFile, File xsltFile, File outputFile) throws TransformerException, FileNotFoundException{
	        			
			// JAXP reads data using the Source interface
	        Source xmlSource = new StreamSource(xmlFile);
	        Source xsltSource = new StreamSource(xsltFile);
	        
	        // the factory pattern supports different XSLT processors
	        SAXTransformerFactory transFact = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	        Transformer trans = transFact.newTransformer(xsltSource);

	        trans.transform(xmlSource, new StreamResult(outputFile));
	        	        
	}
	
	public static void transform(String xmlFilePath,String xsltFilePath, String outputFilePath) throws FileNotFoundException, TransformerException{
		File xmlFile = new File(xmlFilePath);
		File xsltFile = new File(xsltFilePath);
		File outputFile = new File(outputFilePath);
		
		transform(xmlFile, xsltFile, outputFile);
		
	}
	
	
}


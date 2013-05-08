package com.cordys.tools.mb;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.cordys.build.utils.FileSystemUtils;

public class ReportGenerator 
{
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private final Map<String,Element> folderElements = new HashMap<String, Element>();
	
	public void generateReport(List<FileViolations> mbViolationsByFile,File outFile) throws ParserConfigurationException, IOException, TransformerFactoryConfigurationError, TransformerException
	{
		// Generating violations report
		DocumentBuilder docBuilder = dbf.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("mbviolations");
		for (FileViolations fileViolations : mbViolationsByFile) 
		{
			String filepath = fileViolations.getFilePath();
			String componentname = "undefined";
			
			// identifying the component of the file
			filepath = filepath.replaceAll("\\\\", "/");
			int modelersPos = filepath.indexOf("cws/modelers/");
			if ( modelersPos != -1 )
			{
				filepath = filepath.substring(modelersPos+13);
			}
			else
			{	
				int componentsPos = filepath.indexOf("components/");
				if( componentsPos != -1 )
				{
					filepath = filepath.substring(componentsPos+11);
				}
			}
			int pos = filepath.indexOf("/");
			if( pos != -1 )
			{
				componentname = filepath.substring(0,pos);
				filepath = filepath.substring(pos+1);
			}
			Element folderElement = folderElements.get(componentname);
			if( folderElement == null )
			{
				folderElement = doc.createElement("folder");
				folderElement.setAttribute("name", componentname);
				rootElement.appendChild(folderElement);
				folderElements.put(componentname, folderElement);
			}
			
			Element fileElement = doc.createElement("file");			
			fileElement.setAttribute("name", filepath);
			String filetype = fileViolations.getFileType();
			fileElement.setAttribute("type", filetype);			
			folderElement.appendChild(fileElement);
			//Element violationsElement = doc.createElement("violations");
			//fileElement.appendChild(violationsElement);
			List<ReportViolation> violations = fileViolations.getViolations();
			for (ReportViolation violation : violations) 
			{
				Element violationElement = doc.createElement("violation");
				Element descElement = doc.createElement("description");
				violationElement.appendChild(descElement);
				//violationsElement.appendChild(violationElement);
				fileElement.appendChild(violationElement);
				String violationText = violation.getDescription();
				Text vText = doc.createTextNode(violationText);
				descElement.appendChild(vText);
				String linecontent = violation.getLineContent(); 
				if( linecontent != null )
				{
					Element linecontentElement = doc.createElement("linecontent");
					CDATASection lineData = doc.createCDATASection(linecontent);
					linecontentElement.appendChild(lineData);
					violationElement.appendChild(linecontentElement);
				}
				String linenum = violation.getLinenum();
				violationElement.setAttribute("Type", violation.getType());
				violationElement.setAttribute("priority", violation.getPriority());
				violationElement.setAttribute("line", linenum);
				violationElement.setAttribute("ref", violation.getSuggestion());
			}
		}
		
		//Writing to file system
		FileSystemUtils.writeXmlFile(rootElement, outFile);
	}
}

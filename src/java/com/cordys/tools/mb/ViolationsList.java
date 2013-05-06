package com.cordys.tools.mb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class reads the list of violations from the given xml file and prepares the list of all violations  
 */
public class ViolationsList 
{
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private static List<Violation> violationsList = null;
	
	/**
	 * reads the violations from the given xml file
	 * 
	 * xml file structure is as follows
	 * 
	 * <violations>
     *	<violation priority="1">
     *		<vtext>.document</vtext>
     *  </violation>
     *  ............................
     * </violations> 
	 * 
	 * @param violationsFilePath
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static List<Violation> getViolations(String violationsFilePath) throws ParserConfigurationException, SAXException, IOException
	{
		List<Violation> violations = new ArrayList<Violation>();
		DocumentBuilder docBuilder = dbf.newDocumentBuilder();
		Document doc  = docBuilder.parse(new File(violationsFilePath));
		NodeList violationsNodeList = doc.getElementsByTagName("violation");
		int noOfNodes = violationsNodeList.getLength();
		for (int i=0;i<noOfNodes;i++) 
		{
			Violation violation = new Violation();
			Node violationNode = violationsNodeList.item(i);
			String priority = ((Element)violationNode).getAttribute("priority");
			if(priority == null || priority.isEmpty())
				priority = "1";
			violation.setPriority(priority);
			String type = ((Element)violationNode).getAttribute("type");
			if(type != null && !type.isEmpty())
				violation.setViolationType(type);
			String filetypes = ((Element) violationNode).getAttribute("filetypes");
			if(filetypes != null && !filetypes.isEmpty())
			{
				for(String filetype : filetypes.split(","))
				{
					violation.addFileType(filetype);
				}
			}
			Node vTextNode = ((Element)violationNode).getElementsByTagName("vtext").item(0);
			if( vTextNode != null )
			{
				String vText = vTextNode.getFirstChild().getNodeValue();
				violation.setViolationText(vText);
			}
			Node vRegexpNode = ((Element)violationNode).getElementsByTagName("regexp").item(0);
			if( vRegexpNode != null )
			{
				Node rNode = vRegexpNode.getFirstChild();
				if( rNode != null )
				{
					String regexp = rNode.getNodeValue();
					violation.setRegularExpression(regexp);
				}	
			}
			Node vdescriptionNode = ((Element)violationNode).getElementsByTagName("description").item(0);
			if( vdescriptionNode != null )
			{
				Node dNode = vdescriptionNode.getFirstChild();
				if( dNode != null )
				{
					String description = dNode.getNodeValue();
					violation.setDescription(description);
				}
			}
			Node vSuggestionNode = ((Element)violationNode).getElementsByTagName("suggestion").item(0);
			if( vSuggestionNode != null )
			{
				Node sNode = vSuggestionNode.getFirstChild();
				if( sNode != null )
				{
					String suggestion = sNode.getNodeValue();
					violation.setSuggestion(suggestion);
				}	
			}
			Node vExceptionsNode = ((Element) violationNode).getElementsByTagName("exceptions").item(0);
			if(vExceptionsNode != null)
			{
				NodeList regexExceptions = ((Element) vExceptionsNode).getElementsByTagName("regexp");
				for(int j = 0; j < regexExceptions.getLength(); j++)
				{
					Node regexExceptionNode = regexExceptions.item(j).getFirstChild();
					if(regexExceptionNode != null)
					{
						String regexException = regexExceptionNode.getNodeValue();
						violation.addRegexException(regexException);
					}
				}
				NodeList textExceptions = ((Element) vExceptionsNode).getElementsByTagName("vtext");
				for(int j = 0; j < textExceptions.getLength(); j++)
				{
					Node textExceptionNode = textExceptions.item(j).getFirstChild();
					if(textExceptionNode != null)
					{
						String regexException = textExceptionNode.getNodeValue();
						violation.addTextException(regexException);
					}
				}
			}
			Node vExclusionlistNode = ((Element) violationNode).getElementsByTagName("exclusionlist").item(0);
			if(vExclusionlistNode != null)
			{
				NodeList excludeList = ((Element) vExclusionlistNode).getElementsByTagName("exclude");
				for(int j = 0; j < excludeList.getLength(); j++)
				{
					Node excludeListNode = excludeList.item(j).getFirstChild();
					if(excludeListNode != null)
					{
						String excludeListName = excludeListNode.getNodeValue();
						violation.addExclusionlist(excludeListName);
					}
				}
				
			}
			violations.add(violation);
		}
		violationsList = violations;
		return violations;
	}
		
	public static Violation getViolation(String description)
	{
		if(violationsList == null) return null;
		
		Violation matchViolation = new Violation();
		for(Violation violation : violationsList)
		{
			if(description.equals(violation.getDescription()))
			{
				matchViolation = violation;
		
			}
		}
		return matchViolation;
	}
}

package com.cordys.tools.build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ISVPTemplateManager 
{
	private String newlyAddedISVPackages="";
	private String allISVPpackages="";
	private String isvpackagesFolderPath;
	private String isvpTemplatePath = ".";
	private String isvpPropertiesFilePath;
	private String baseTemplatePath;
	private Map<String,Element> isvpsInBaseTemplate = new HashMap<String, Element>(); 
	
	public static final String ISVP_BUILD_NUM = "build.number";
	public static final String ISVP_OWNER = "isv.owner";
	public static final String ISVP_VERSION = "isv.version";
	
	public static final String ISVP_TEMPLATE_ROOT = "isvpcommand";
	public static final String ISVP_ELEMENT = "isvp";
	
	private String buildnumber;
	private String isvpowner;
	private String isvpversion;

	private static final String DOC_ISVP_NAME = "Cordys Documentation.isvp";
	
	private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private static XPathFactory xpathFact = XPathFactory.newInstance();
	
	/**
	 * comma separated list of isvp packages that are part of the cumulative update
	 * 
	 * @param allISVPackages the allISVPackages to set
	 */
	public void setAllISVPackages(String allISVPackages) {
		this.allISVPpackages = allISVPackages;
	}
	
	/**
	 * comma separated list of isvp packages that are newly added in cumulative update after main release
	 * 
	 * @param newlyAddedISVPackages the newlyAddedISVPackages to set
	 */
	public void setNewlyAddedISVPackages(String newlyAddedISVPackages) {
		this.newlyAddedISVPackages = newlyAddedISVPackages;
	}

	/**
	 * @param isvpTemplatePath the isvpTemplatePath to set
	 */
	public void setIsvpTemplatePath(String isvpTemplatePath) {
		this.isvpTemplatePath = isvpTemplatePath;
	}

	/**
	 * @param isvpackagesFolderPath the isvpackagesFolderPath to set
	 */
	public void setIsvpackagesFolderPath(String isvpackagesFolderPath) {
		this.isvpackagesFolderPath = isvpackagesFolderPath;
	}

	/**
	 * @param isvpPropertiesFilePath the isvpPropertiesFilePath to set
	 */
	public void setIsvpPropertiesFilePath(String isvpPropertiesFilePath) {
		this.isvpPropertiesFilePath = isvpPropertiesFilePath;
	}

	/**
	 * @param buildnumber the buildnumber to set
	 */
	public void setBuildnumber(String buildnumber) {
		this.buildnumber = buildnumber;
	}

	/**
	 * @param isvpowner the isvpowner to set
	 */
	public void setIsvpowner(String isvpowner) {
		this.isvpowner = isvpowner;
	}

	/**
	 * @param isvpversion the isvpversion to set
	 */
	public void setIsvpversion(String isvpversion) {
		this.isvpversion = isvpversion;
	}
	
	/**
	 * includes the template of thebase version 
	 */
	public void setBaseTemplatePath(String baseTemplatePath)
	{
		this.baseTemplatePath = baseTemplatePath;
	}

	/**
	 * generates the isvp template
	 * 
	 * @throws TemplateGenerationException
	 */
	public void generateUpgradeISVPTemplate() throws TemplateGenerationException
	{
		if( isvpackagesFolderPath == null )
		{
			throw new TemplateGenerationException("isvp packages folder path is null.");
		}
		File isvpackagesFolder = new File(isvpackagesFolderPath);
		if( !isvpackagesFolder.isDirectory() )
		{
			throw new TemplateGenerationException(isvpackagesFolderPath + " directory is not found.");
		}
		Properties isvpProperties = new Properties();
		try {
			isvpProperties.load(new FileReader(isvpPropertiesFilePath));
		}
		catch (Exception e) 
		{
			//System.out.println("isvp properties file is not using.");
			isvpProperties = null;
			//throw new TemplateGenerationException(e.getMessage(),e);
		}
		if( isvpProperties != null )
		{
			buildnumber = isvpProperties.getProperty(ISVP_BUILD_NUM);
			isvpowner = isvpProperties.getProperty(ISVP_OWNER);
			isvpversion = isvpProperties.getProperty(ISVP_VERSION);
		}
		DocumentBuilder docBuilder;
		Document document;
		Element isvpcommand = null;
		try 
		{
			docBuilder = dbf.newDocumentBuilder();
			if( baseTemplatePath == null )
			{
				document = docBuilder.newDocument();
				isvpcommand = document.createElement(ISVP_TEMPLATE_ROOT);
			}
			else
			{
				System.out.println("Reading the base ievp template :" + baseTemplatePath);
				document = docBuilder.parse(new FileInputStream(baseTemplatePath));
				isvpcommand = document.getDocumentElement();
				NodeList isvpNodes = document.getElementsByTagName("isvp");
				for( int i=0;i<isvpNodes.getLength();i++)
				{
					Element isvpNode = (Element) isvpNodes.item(i);
					String isvpname = isvpNode.getAttribute("name");
					isvpsInBaseTemplate.put(isvpname, isvpNode);
				}
			}
			
		} catch (Exception e1) {
			throw new TemplateGenerationException("Failed to create document:"+e1.getMessage(),e1);
		}
		
			
		// List the files to be parsed
		FilenameFilter isvpfilter = new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("isvp"); 
			}
		};
		File[] isvpFiles = FileUtil.listFilesOnLastModifiedDate(isvpackagesFolder,isvpfilter);
		//System.out.println("isvps length:"+isvpFiles.length);
		//System.out.println(allISVPpackages);
		//System.out.println(newlyAddedISVPackages);
		for (File isvpFile : isvpFiles) 
		{
			String isvpFileName = isvpFile.getName();
			if( isvpFileName.equalsIgnoreCase(DOC_ISVP_NAME) )
			{
				continue; // ignoring the upgrade template generation for documentation isvp
			}
			String isvpcn = isvpFileName;
			int dotpos = isvpFileName.lastIndexOf(".");
			if( dotpos > 0 )
			{
				isvpcn = isvpFileName.substring(0,dotpos);
			}
			else
			{
				System.out.println("Invalid filename:"+isvpFileName);
				continue;
			}
			if ( !allISVPpackages.contains(isvpFileName) )
			{
				//System.out.println(isvpFileName +" is not part of cumulative update.");
				continue;
			}
			Element isvpElement = null;
			isvpElement = isvpsInBaseTemplate.get(isvpcn);
			if( isvpElement != null ) // isvp is already available in base template
			{
				// update the build number in base template isvp node
				XPath xpath = xpathFact.newXPath();
				try {
					Node node = (Node)xpath.evaluate(".//build", isvpElement, XPathConstants.NODE);
					node.getFirstChild().setNodeValue(buildnumber);
				} catch (XPathExpressionException e) {
					System.out.println("Failed to update build number for isvp '"+isvpcn+"'."+e.getMessage());
				}
			}
			else
			{
				isvpElement = document.createElement(ISVP_ELEMENT);
				isvpElement.setAttribute("name", isvpcn);
				isvpElement.setAttribute("skip", "false");
				isvpcommand.appendChild(isvpElement);
				ISVPackage isvPackage = new ISVPackage();
				isvPackage.setBuildnumber(buildnumber);
				isvPackage.setOwner(isvpowner);
				isvPackage.setVersion(isvpversion);
				isvPackage.setWcpversion(isvpversion);
				isvPackage.setIsvpfilename(isvpFileName);
				isvPackage.setIsvpcn(isvpcn);
				String isvpname = isvpcn;
				if( isvpcn.startsWith(isvpowner) )
					isvpname = isvpcn.substring(isvpowner.length()+1);
				isvPackage.setIsvpname(isvpname);
				ISVPTemplate isvpTemplate = new ISVPTemplate();
				Element reqElement;
				try 
				{
					if( newlyAddedISVPackages.contains(isvpFileName) )
					{
						//System.out.println("Ignoring the newly added isvpackage '"+isvpFileName+"'in upgrade template.");
						//continue;
						reqElement = isvpTemplate.createLoadTemplate(isvPackage,document);
					}
					else
					{
						reqElement = isvpTemplate.createUpgradeTemplate(isvPackage,document);
					}
				} catch (Exception e)
				{
					throw new TemplateGenerationException(e.getMessage(),e);
				}
				isvpElement.appendChild(reqElement);
			}	
		}
		
		// writing the template to the file system
		try {
			FileUtil.writeXmlFile(isvpcommand, isvpTemplatePath);
		} catch (Exception e) {
			throw new TemplateGenerationException("Failed to write to file:"+isvpTemplatePath+" ,"+e.getMessage(),e);
		}
		System.out.println(isvpTemplatePath +" upgrade template generated successfully.");
	}
	
	public static void main(String[] args) throws Exception 
	{
		ISVPTemplateManager isvpTempManager = new ISVPTemplateManager();
		isvpTempManager.setIsvpackagesFolderPath("D:\\isvpackages");
		isvpTempManager.setBuildnumber("14");
		isvpTempManager.setIsvpowner("Cordys");
		isvpTempManager.setIsvpversion("D1.000.005");
		//isvpTempManager.setIsvpPropertiesFilePath("D:\\isvpackages\\build.properties");
		isvpTempManager.setBaseTemplatePath("D:\\isvpackages\\Copy of UpgradeTemplate");
		isvpTempManager.setIsvpTemplatePath("D:\\isvpackages\\UpgradeTemplate");
		isvpTempManager.setNewlyAddedISVPackages("Cordys Notification Runtime.isvp");
		isvpTempManager.setAllISVPackages("Cordys CWS Core.isvp,Cordys Single Sign-On.isvp,Cordys User Management.isvp,Cordys Document Store.isvp,cars,Cordys Business Process Engine.isvp,Cordys Notification.isvp,Cordys Security Admin.isvp,Cordys Security Admin Core.isvp,Cordys Rule Engine.isvp,Cordys Tag Server.isvp,Cordys WS-AppServer.isvp,Cordys Business Activity Monitoring.isvp,Cordys XForms Runtime.isvp,Cordys ACL Modeler Client.isvp,Cordys ACL Modeler Server.isvp,Cordys Application Connector Client.isvp,Cordys Application Connector Server.isvp,Cordys Common Attachments.isvp,Cordys BAM Modeler.isvp,Cordys Business Context Modeler.isvp,Cordys Business Process Designer.isvp,Cordys BPM Export Plugins.isvp,Cordys BPM Import Plugins.isvp,Cordys BPM Model Components.isvp,Cordys Business Context Modeler.isvp,Cordys Business Calendar Modeler.isvp,Cordys Business Calendar Runtime.isvp,Cordys Business Object Document.isvp,Cordys Case Management Modeler.isvp,Cordys Collaborative Workspace.isvp,Cordys Common Modeler Components.isvp,Cordys Content Map Modeler.isvp,Cordys CWS Document Exchange Runtime.isvp,Cordys CWS Plugin Environment Runtime.isvp,Cordys Data Transformation Modeler.isvp,Cordys Database Schema Modeler.isvp,Cordys Dispatch Algorithm Modeler.isvp,Cordys E-mail Modeler.isvp,Cordys External Web Service Modeler.isvp,Cordys Graphical Modeling Framework.isvp,Cordys Inbox Modeler.isvp,Cordys Java Call Modeler.isvp,Cordys KPI Modeler.isvp,Cordys MDM Modeler.isvp,Cordys Notification Runtime.isvp,Cordys Object Template Modeler.isvp,Cordys Organization Modeler.isvp,Cordys Organization Model Runtime.isvp,Cordys Role Modeler Client.isvp,Cordys Role Modeler Server.isvp,Cordys RoleBase Server.isvp,Cordys Rule Modeler.isvp,Cordys Schedule Modeler.isvp,Cordys TaskModel Client.isvp,Cordys Task Modeler Server.isvp,Cordys Translation Modeler Client.isvp,Cordys Translation Modeler Server.isvp,Cordys User Assignment.isvp,Cordys User Interface Modeler Client.isvp,Cordys User Interface Modeler Server.isvp,Cordys Value Chain Modeler.isvp,Cordys Value Chain Modeler.isvp,Cordys Web Service Modeler Client.isvp,Cordys Web Service Modeler Server.isvp,Cordys Workflow Delivery Modeler.isvp,Cordys Worklist Modeler.isvp,Cordys WS-AppServer Modeler.isvp,Cordys XForms Modeler Client.isvp,Cordys XForms Modeler Server.isvp,Cordys XML Schema Client.isvp,Cordys XML Schema Server.isvp,Cordys XMLStore Modeler.isvp,Cordys CWS Synchronizer.isvp,Cordys CWS SVNAdapter Plugin.isvp,Cordys CWS Runtime XForms.isvp,Cordys Common Document Types.isvp,Cordys Common Modeler Components.isvp,Cordys CWS Bootstrap Helper.isvp,Cordys Single Sign-On XForms.isvp,Cordys Scheduler.isvp,Cordys Task Server.isvp,Cordys XMLStore.isvp,Cordys UDDI Connector.isvp,Cordys Documentation.isvp,Cordys CoBOC.isvp,Cordys Common Content.isvp,baseLine");
		isvpTempManager.generateUpgradeISVPTemplate();
		System.out.println("done.");
	}
}

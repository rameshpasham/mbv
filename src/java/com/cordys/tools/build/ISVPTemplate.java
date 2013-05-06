package com.cordys.tools.build;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class ISVPTemplate 
{
	public static final String UPGRADEISV_REQ_NAME = "UpgradeISVPackage";
	public static final String UPGRADE_REQ_NS = "http://schemas.cordys.com/1.0/isvpackage";
	public static final String ISVP_URL_TAG_NAME = "url";
	public static final String LOADISV_REQ_NAME = "LoadISVPackage"; 
	public static final String LOAD_REQ_NS = "http://schemas.cordys.com/1.0/isvpackage";
	
	private Element createISVPackageDef(ISVPackage isvPackage,Document document) throws ParserConfigurationException
	{
		Element isvpackage = document.createElementNS(ISVPackage.ISVP_NS,ISVPackage.ISVP_PACKAGE);
		isvpackage.setAttribute("file", isvPackage.getIsvpfilename());
		Element descElement = document.createElement(ISVPackage.DESCRIPTION);
		isvpackage.appendChild(descElement);
		Element ownerElement = document.createElement(ISVPackage.ISVP_OWNER);
		Text owner = document.createTextNode(isvPackage.getOwner());
		ownerElement.appendChild(owner);
		descElement.appendChild(ownerElement);
		Element nameElement = document.createElement(ISVPackage.ISVP_NAME);
		Text name = document.createTextNode(isvPackage.getIsvpname());
		nameElement.appendChild(name);
		descElement.appendChild(nameElement);
		Element cnElement = document.createElement(ISVPackage.ISVP_CN);
		Text isvpcn = document.createTextNode(isvPackage.getIsvpcn());
		cnElement.appendChild(isvpcn);
		descElement.appendChild(cnElement);
		Element versionElement = document.createElement(ISVPackage.ISVP_VERSION);
		Text version = document.createTextNode(isvPackage.getVersion());
		versionElement.appendChild(version);
		descElement.appendChild(versionElement);
		Element wcpversionElement = document.createElement(ISVPackage.ISVP_WCPVERSION);
		Text wcpversion = document.createTextNode(isvPackage.getWcpversion());
		wcpversionElement.appendChild(wcpversion);
		descElement.appendChild(wcpversionElement);
		Element buildElement = document.createElement(ISVPackage.ISVP_BUILD);
		Text buildnum = document.createTextNode(isvPackage.getBuildnumber());
		buildElement.appendChild(buildnum);
		descElement.appendChild(buildElement);
		Element textdescElement = document.createElement(ISVPackage.ISVP_TEXTDESC);
		descElement.appendChild(textdescElement);
		Element eulaElement = document.createElement(ISVPackage.ISVP_EULA);
		descElement.appendChild(eulaElement);
		eulaElement.setAttribute("source", "");
		Element sidebarElement = document.createElement(ISVPackage.ISVP_SIDEBAR);
		descElement.appendChild(sidebarElement);
		sidebarElement.setAttribute("source", "");
		return isvpackage;
	}
	
	public Element createUpgradeTemplate(ISVPackage isvPackage,Document document) throws ParserConfigurationException
	{
		Element upgradeElement = document.createElementNS(UPGRADE_REQ_NS,UPGRADEISV_REQ_NAME);
		upgradeElement.setAttribute("deletereference", "false");
		Element urlElement = document.createElement(ISVP_URL_TAG_NAME);
		upgradeElement.appendChild(urlElement);
		Text urlText = document.createTextNode(getURL(isvPackage.getIsvpfilename()));
		urlElement.appendChild(urlText);
		Element isvpackageElement = createISVPackageDef(isvPackage,document);
		upgradeElement.appendChild(isvpackageElement);
		return upgradeElement;
	}

	public Element createLoadTemplate(ISVPackage isvPackage,Document document) throws ParserConfigurationException
	{
		Element loadElement = document.createElementNS(LOAD_REQ_NS,LOADISV_REQ_NAME);
		Element urlElement = document.createElement(ISVP_URL_TAG_NAME);
		loadElement.appendChild(urlElement);
		Text urlText = document.createTextNode(getURL(isvPackage.getIsvpfilename()));
		urlElement.appendChild(urlText);
		Element isvpackageElement = createISVPackageDef(isvPackage,document);
		loadElement.appendChild(isvpackageElement);
		return loadElement;
	}
	
	private String getURL(String isvpfilename)
	{
		return "http://machine/cordys/wcp/isvcontent/packages/com.cordys.web.isvp.DownLoadISVP.wcp?isvpname="+isvpfilename;
	}
}

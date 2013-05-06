package com.cordys.tools.build;

public class ISVPackage 
{
	public static final String ISVP_PACKAGE = "ISVPackage";
	public static final String DESCRIPTION = "description";
	public static final String ISVP_OWNER = "owner";
	public static final String ISVP_CN = "cn";
	public static final String ISVP_NAME = "name";
	public static final String ISVP_VERSION = "version";
	public static final String ISVP_WCPVERSION = "wcpversion";
	public static final String ISVP_BUILD = "build";
	public static final String ISVP_TEXTDESC = "textdescription";
	public static final String ISVP_EULA = "eula";
	public static final String ISVP_SIDEBAR = "sidebar";
	
	public static final String ISVP_NS = "http://schemas.cordys.com/1.0/isvpackage";
	
	private String isvpfilename;
	private String isvpname="";
	private String isvpcn="";
	private String owner="";
	private String version="0";
	private String wcpversion="0";
	private String buildnumber="0";
	private String textdescription="";
	private String isvpNS = ISVP_NS;
	
	
	/**
	 * @return the isvpfilename
	 */
	public String getIsvpfilename() {
		return isvpfilename;
	}

	/**
	 * @return the isvpname
	 */
	public String getIsvpname() {
		return isvpname;
	}
	/**
	 * @return the isvpcn
	 */
	public String getIsvpcn() {
		return isvpcn;
	}
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @return the wcpversion
	 */
	public String getWcpversion() {
		return wcpversion;
	}
	/**
	 * @return the buildnumber
	 */
	public String getBuildnumber() {
		return buildnumber;
	}
	/**
	 * @return the textdescription
	 */
	public String getTextdescription() {
		return textdescription;
	}
	/**
	 * @return the isvpNS
	 */
	public String getIsvpNS() {
		return isvpNS;
	}
	/**
	 * @param isvpfilename the isvpfilename to set
	 */
	public void setIsvpfilename(String isvpfilename) {
		this.isvpfilename = isvpfilename;
	}
	/**
	 * @param isvpname the isvpname to set
	 */
	public void setIsvpname(String isvpname) {
		this.isvpname = isvpname;
	}
	/**
	 * @param isvpcn the isvpcn to set
	 */
	public void setIsvpcn(String isvpcn) {
		this.isvpcn = isvpcn;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @param wcpversion the wcpversion to set
	 */
	public void setWcpversion(String wcpversion) {
		this.wcpversion = wcpversion;
	}
	/**
	 * @param buildnumber the buildnumber to set
	 */
	public void setBuildnumber(String buildnumber) {
		this.buildnumber = buildnumber;
	}
	/**
	 * @param textdescription the textdescription to set
	 */
	public void setTextdescription(String textdescription) {
		this.textdescription = textdescription;
	}
	/**
	 * @param isvpNS the isvpNS to set
	 */
	public void setIsvpNS(String isvpNS) {
		this.isvpNS = isvpNS;
	}
}

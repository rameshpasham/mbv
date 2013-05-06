package com.cordys.tools.mb;

import java.util.HashSet;
import java.util.Set;

public class Violation
{
	private String text;
	private String priority = "1";
	private String description = "";
	private String suggestion = "";
	private String regexp;
	private String type;
	private Set<String> regexExceptions = new HashSet<String>();
	private Set<String> textExceptions = new HashSet<String>();
	private Set<String> fileTypes = new HashSet<String>();
	private Set<String> exclusionlist = new HashSet<String>();
	/**
	 * @return the violation text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @return the violation type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @return the violation text
	 */
	public String getRegExpression()
	{
		return regexp;
	}

	/**
	 * @return the violation priority
	 */
	public String getPriority()
	{
		return priority;
	}

	/**
	 * @return the violation description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return the suggestion to be used in place of violation
	 */
	public String getSuggestion()
	{
		return suggestion;
	}

	public Set<String> getRegexExceptions()
	{
		return this.regexExceptions;
	}

	public Set<String> getTextExceptions()
	{
		return this.textExceptions;
	}

	public Set<String> getExclusionlist() {
		return this.exclusionlist;
	}

	public boolean isApplicable(String fileType)
	{
		if(this.fileTypes.isEmpty()) return true;
		if(this.fileTypes.contains(fileType)) return true;
		return false;
	}

	/**
	 * sets the violation text
	 * 
	 * @param vtext
	 */
	public void setViolationText(String vtext)
	{
		this.text = vtext;
	}

	/**
	 * sets the violation type
	 * 
	 * @param vtext
	 */
	public void setViolationType(String vtype)
	{
		this.type = vtype;
	}
	
	/**
	 * sets the regular expression to search
	 * 
	 * @param regexp
	 */
	public void setRegularExpression(String regexp)
	{
		this.regexp = regexp;
	}

	/**
	 * @param the priority of the violation
	 */
	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	/**
	 * @param the description of the violation
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @param suggestion to use in case of violation
	 */
	public void setSuggestion(String suggestion)
	{
		this.suggestion = suggestion;
	}

	public void addRegexException(String regex)
	{
		this.regexExceptions.add(regex);
	}
	public void addExclusionlist(String exlist)
	{
		this.exclusionlist.add(exlist);
	}

	public void addTextException(String text)
	{
		this.textExceptions.add(text);
	}

	public void addFileType(String fileType)
	{
		this.fileTypes.add(fileType);
	}
}

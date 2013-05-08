package com.cordys.tools.mb.gradle

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.file.FileTree;

class MbvPluginExtention {
	
	private final Project project
	File violationsFile
	FileTree srcFolder
	boolean showLines
	boolean ignoreMarker
	File outFile
	
	MbvPluginExtention(Project project){
		this.project = project
		this.showLines = false
		this.ignoreMarker = false
		/*TODO : violationFile should be take from jar file */
		this.violationsFile = project.rootProject.file('common-scripts/mb-violations.xml')
		/*TODO : logic should be simple to find the source */
		this.srcFolder = getSrcFolder(project)
		this.outFile = project.file('build/reports/mbv/main.xml')
	}
	
	public static FileTree getSrcFolder(Project project)
	{
		
		def srcRootFolder = getSrcRoot(project)
		if(srcRootFolder ==  null ) srcRootFolder =  'src'
		String excludeFiles = project.getProperties().get("mbv.exclude")
		String excludePattern = (excludeFiles != null ) ? excludeFiles : ""
		FileTree srcFolder =  project.fileTree(dir : srcRootFolder + "/web", excludes : [excludePattern])
	    return srcFolder
   }
	
	def static getSrcRoot(Project project)
	{
	 project.getProjectDir().list().find{ srcRootFolder ->
	 if(srcRootFolder.endsWith("_DT") || srcRootFolder.endsWith("_dt"))
	 {
		  return srcRootFolder
	 }
	 
	 }
	 
 }
}
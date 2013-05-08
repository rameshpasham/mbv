package com.cordys.tools.mb.gradle

import java.io.File;
import java.util.Set;

import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.api.file.FileTree

import com.cordys.tools.mb.CordysMBViolationsDetector
import com.cordys.tools.mb.WebFileFilter



class MbvMain extends DefaultTask{
	
	FileTree srcFolder 
	File outFile
	File violationsFile
	boolean  ignoreMarker 
	boolean showLines
	
	@TaskAction
	void run()
	{
		if(srcFolder != null) 
		{
		 Set<File> fileSet = srcFolder.getFiles()
		 doAction(fileSet, outFile , violationsFile)
		}
		else 
		{
			println "No Source available for project"	
		} 
    }
	
	def doAction(Set<File> srcFolder, File outFile, File violationsFile){
		def mbDetector = new CordysMBViolationsDetector()
		def fileFilter = new WebFileFilter()
		mbDetector.setFilenameFilter(fileFilter)
		mbDetector.setShowLines(showLines);
		mbDetector.setIgnoreMarker(ignoreMarker)
		mbDetector.generateViolationsReport(violationsFile, srcFolder, outFile)
		
  }
	
	
}


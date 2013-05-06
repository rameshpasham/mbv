package com.cordys.tools.mb.gradle

import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.api.file.FileTree
import org.gradle.api.internal.project.IsolatedAntBuilder
import org.gradle.internal.reflect.Instantiator

import com.cordys.tools.mb.CordysMBViolationsDetector
import com.cordys.tools.mb.WebFileFilter



class MbvTask extends DefaultTask{
	String violationsFile
		
	/*
	MbvTask(){
		def mbvplugin  = new MbvPlugin()
		violationsFile  =   mbvplugin.violationsFile
		FileTree srcFolder       = mbvplugin.srcFolder
		String outFile         =   mbvplugin.outFile
		boolean showlines      =  mbvplugin.showlines
		boolean ignoreMarker   =   mbvplugin.ignoreMarker
		}*/
	 //def ant = new groovy.util.AntBuilder()
	
	/*@Nested
	private final IsolatedAntBuilder antBuilder
	
	@Inject
	MbvTask(Instantiator instantiator, IsolatedAntBuilder antBuilder) {
		this.antBuilder = antBuilder
		
	}*/
	/*FileTree srcFolder = "${project.mbv.srcFolder}"
	String outFile = "${project.mbv.outFile}"*/
	@TaskAction
	void run()
	{
		println "rameshbabau"
	//	println violationsFile
		println "${project.mbv.srcFolder}"
		
	
	   /*
	    * doubt giving class path
	    *  ant.taskdef(name:'mbviolation', classname:'com.cordys.tools.tasks.MBViolationDetector')
	    ant.mbviolation(outfilepath : '${project.mbv.outFile}', showlines : 'false', violationsxmlfile : '${project.mbv.violationsFile}')
		{
			srcFolder {
				  ant.fileset('src/web')
			}
				  
		}
		ant.echo("ant is running")
		*/
		/*antBuilder.execute  {
			ant.taskdef(name: 'mbviolation', classname: 'com.cordys.tools.tasks.MBViolationDetector')
			 ant.mbviolation(outfilepath : '${project.mbv.outFile}', showlines : 'false', violationsxmlfile : '${project.mbv.violationsFile}') 
			 {
				
			 }
		}
	    */
		/*validateConfiguration()
		
		def mbDetector = new CordysMBViolationsDetector()
		def fileFilter = new WebFileFilter()
		mbDetector.setFilenameFilter(fileFilter)
		mbDetector.setShowLines(showlines);
		mbDetector.setIgnoreMarker(ignoreMarker);*/
		
		/*if(srcFolder != null){
		mbDetector.generateViolationsReport(violationsFile, srcFolder, outFile)}
		else{
		mbDetector.generateViolationsReport(violationsFile, projectDir.list(), outFile)
		
		println "report file: ${outFile}"
	      }
		*/
		
	
	
 
	}
	/*private void validateConfiguration()
	{
		if(violationsFile == null) throw new InvalidUserDataException("Please specify the violations file")
		if(outFile == null) throw new InvalidUserDataException("Please specify the output file")
		if(srcFolder == null) throw new InvalidUserDataException("Please specify a srcfolder")
		
	}*/
	
	}

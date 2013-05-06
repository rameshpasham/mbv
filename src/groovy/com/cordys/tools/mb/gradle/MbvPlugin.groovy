package com.cordys.tools.mb.gradle

import org.gradle.api.*
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;

import com.cordys.tools.mb.CordysMBViolationsDetector;
import com.cordys.tools.mb.WebFileFilter


class MbvPlugin implements Plugin<Project> {
	
	String violationsFile = "mb-violations.xml"
	//File srcFolder  = 'src/web'
	@InputDirectory
	FileTree srcFolder
	@OutputDirectory
	String outFile
	
	boolean showlines = false
	boolean ignoreMarker = false
	
	//Finding the srcRoot folder of Project
		
	@Override
	public void apply(Project project)  {
		def srcRootFolder = getSrcRoot(project)
		if(srcRootFolder ==  null ) srcRootFolder =  'src'
		println  "srcFolder = " + srcRootFolder
		String excludeFiles = project.getProperties().get("mbv.exclude")
		String excludePattern = (excludeFiles != null ) ? excludeFiles : ""
		srcFolder =  project.fileTree(dir : srcRootFolder + "/web", excludes : [excludePattern])
		project.extensions.create('mbv', MbvPlugin.class)
		project.task('mbvMain') {
		//Deleting the mbv folder
		project.delete('build/reports/mbv')
		//making directory in project folder
		project.mkdir('build/reports/mbv')
		outFile  = project.getName() + "/build/reports/mbv/main.xml"
	    Set<File> fileset = srcFolder.getFiles()
		//calling the generation report 
		doAction(fileset, outFile)
	}
	

}
    

	def doAction(Set<File> srcFolder, String outFile){
	    def mbDetector = new CordysMBViolationsDetector()
		def fileFilter = new WebFileFilter()
		mbDetector.setFilenameFilter(fileFilter)
		mbDetector.setShowLines(showlines);
		mbDetector.setIgnoreMarker(ignoreMarker)
		if(srcFolder != null){
			mbDetector.generateViolationsReport(violationsFile, srcFolder, outFile)
		}
        else{
			println "No source avilable for project"
		}
  }
	
   def getSrcRoot(Project project)
   	{
        println "project Abs path " + project.getName()
	    
		println project.getProjectDir()
		project.getProjectDir().list().find{ srcRootFolder ->
		if(srcRootFolder.endsWith("_DT") || srcRootFolder.endsWith("_dt"))
		{
			 return srcRootFolder
		}
		
	    }
		
	}
	
}

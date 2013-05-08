package com.cordys.tools.mb.gradle

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;




class MbvReport extends DefaultTask{
 
 File inputFile
 File xsltFile
 File outputFile
 
 @TaskAction
 
 void generateReport(){
	 
	/*TODO : Using ant for generating html page*/
	project.ant.xslt(in : inputFile , style: xsltFile ,out: outputFile)
		
		


  }
 
}

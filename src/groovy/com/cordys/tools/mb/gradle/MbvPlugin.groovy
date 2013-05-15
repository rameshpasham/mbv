package com.cordys.tools.mb.gradle

import org.gradle.api.*
import org.gradle.testfixtures.ProjectBuilder;
/*  It is the starting point of gradle custom plugin.
 *  We should say that this class is implementation class in META-INF/gradle-plugin/mbv.properties for gradle detecting.
 *  In apply method, we can add our own tasks to the plugin.
 */

class MbvPlugin implements Plugin<Project> {

	private MbvPluginExtention extention

	@Override
	public void apply(Project project)  {

        extention = project.extensions.create('mbv', MbvPluginExtention, project)
                  
		
		/* TODO: removing duplication extention multiple times */ 
		
		project.task('mbvMain', type :MbvMain).with {
			srcFolder = extention.srcFolder
			showLines = extention.showLines
			ignoreMarker = extention.ignoreMarker
			violationsFile = extention.violationsFile
			outFile = extention.outFile
		}
		project.tasks.findByName("mbvMain").doFirst {
			//Deleting the mbv folder
			project.delete('build/reports/mbv')
			//making directory in project folder
			project.mkdir('build/reports/mbv')
		}
		
		project.task('MbvReport', type :MbvReport, dependsOn : "mbvMain").with{
			
			inputFile = extention.outFile
			xsltFile  = project.rootProject.file("common-scripts/mb-componentview.xslt")
			outputFile = project.file("build/reports/mbv/main.html")
		}
		
		
	}
	
	/*static main(args){
		      final Project project = ProjectBuilder.builder().build()
			  project.apply(plugin : MbvPlugin)
			  project.tasks.findByName("mbvMain").getActions().execute()
			  
	}*/
	
}



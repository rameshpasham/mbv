mbv plugin
===


A plugin for adding reporting multibrowser voilations to the Gradle build tool.


Adding the plugin:


buildscript {
    
    dependencies { classpath 'com.cordys:mbv:4.3' }
}
apply plugin: com.cordys.tools.mb.gradle.MbvPlugin




Running the Tasks:

gradle mbvMain

it generates the xml file(in project directory build/report/mbv/main.xml) which can be parsed by jenkins muvi plugin. 

gradle mbvReport

it generates custom html file(build/report/mbv/main.html) per folder.

You can exclude some folders, files using pattern with comma seperated files 
putting a property in your gradle.properties as 
mbv.exclude =  xformspreview.htm,flash/*



Note :
Assuming the web folder should be under src for runtime projects and in componentname_dt for design time components.



package com.cordys.tools.mbv.gradle;

import org.gradle.api.*
import org.gradle.testfixtures.ProjectBuilder
import org.junit.*

class MbvPluginTest {
	@Test
	void mbvTaskIsAddedToProject() {
	final Project project = ProjectBuilder.builder().build()
	project.apply plugin : 'mbv'
	assert project.tasks.findByName('mbv')
	}
	/*@Test
	void configurePrefix() {
	final Project project = ProjectBuilder.builder().build()
	project.apply plugin: sample.InfoPlugin
	project.info.prefix = 'Sample'
	assert project.info.prefix == 'Sample'
	}*/
}

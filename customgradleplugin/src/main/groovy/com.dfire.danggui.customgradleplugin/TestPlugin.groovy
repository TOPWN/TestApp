package com.dfire.danggui.customgradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class TestPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('testTask') << {
            println "Hello gradle plugin"
        }
        project.gradle.addListener(new TimeListener())
    }
}
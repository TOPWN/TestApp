package com.dfire.danggui.customgradleplugin

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class UploadArchivesPlugin implements Plugin<Project> {

    public static final String UPDATE_PROPERTY_NAME = 'update.properties'
    Project mProject
    Set<Project> allProject

    void apply(Project project) {
        mProject = project
        ArrayList<Task> firstLevelUpdatePro = new ArrayList<>()
        ArrayList<Task> secondLevelUpdatePro = new ArrayList<>()
        ArrayList thirdLevelUpdatePro = new ArrayList<>()
        ArrayList<Task> fourLevelUpdatePro = new ArrayList<>()
        ArrayList<Task> fiveLevelUpdatePro = new ArrayList<>()
        project.afterEvaluate {
            File updateFile = getUpdateFile()
            if (!updateFile.exists()) {
                throw new GradleException('You need put the update.properties file in project root')
            }
            allProject = project.rootProject.allprojects
            def props = new Properties()
            props.load(new FileInputStream(updateFile))
            def firstLevel = props.getProperty("FIRST_LEVEL")
            def secondLevel = props.getProperty("SECOND_LEVEL")
            def thirdLevel = props.getProperty("THIRD_LEVEL")
            def fourLevel = props.getProperty("FOUR_LEVEL")
            def fiveLevel = props.getProperty("FIVE_LEVEL")
            initLevelProjectList(firstLevel, firstLevelUpdatePro)
            initLevelProjectList(secondLevel, secondLevelUpdatePro)
            initLevelProjectList(thirdLevel, thirdLevelUpdatePro)
            initLevelProjectList(fourLevel, fourLevelUpdatePro)
            initLevelProjectList(fiveLevel, fiveLevelUpdatePro)
            if (!projectUploadDependsOn(fiveLevelUpdatePro, fourLevelUpdatePro)) {
                if (!projectUploadDependsOn(fiveLevelUpdatePro, thirdLevelUpdatePro)) {
                    if (!projectUploadDependsOn(fiveLevelUpdatePro, secondLevelUpdatePro)) {
                        projectUploadDependsOn(fiveLevelUpdatePro, firstLevelUpdatePro)
                    }
                }
            }
            if (!projectUploadDependsOn(fourLevelUpdatePro, thirdLevelUpdatePro)) {
                if (!projectUploadDependsOn(fourLevelUpdatePro, secondLevelUpdatePro)) {
                    projectUploadDependsOn(fourLevelUpdatePro, firstLevelUpdatePro)
                }
            }
            if (!projectUploadDependsOn(thirdLevelUpdatePro, secondLevelUpdatePro)) {
                projectUploadDependsOn(thirdLevelUpdatePro, firstLevelUpdatePro)
            }
            projectUploadDependsOn(secondLevelUpdatePro, firstLevelUpdatePro)

            Task uploadTask = project.task('uploadTask') << {
                println "Upload to Nexus Completed"
            }
            if (!taskUploadDependsOn(uploadTask, fiveLevelUpdatePro)) {
                if (!taskUploadDependsOn(uploadTask, fourLevelUpdatePro)) {
                    if (!taskUploadDependsOn(uploadTask, thirdLevelUpdatePro)) {
                        if (!taskUploadDependsOn(uploadTask, secondLevelUpdatePro)) {
                            taskUploadDependsOn(uploadTask, firstLevelUpdatePro)
                        }
                    }
                }
            }
        }
    }

    def getUpdateFile() {
        return new File("${mProject.rootDir}/${UPDATE_PROPERTY_NAME}")
    }

    def initLevelProjectList(def levelProperty, ArrayList<Task> updateProjects) {
        if (null != levelProperty && !levelProperty.empty) {
            def propertyString = levelProperty.substring(1, levelProperty.length() - 1)
            def propertyArray = propertyString.split(',').collect { it as String }
            if (null != allProject) {
                propertyArray.each { property ->
                    allProject.each { project ->
                        if (project.name.contains(property)) {
                            project.tasks.each { Task task ->
                                if (task.name.contains("uploadArchives")) {
                                    updateProjects.add(task)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    static boolean projectUploadDependsOn(ArrayList<Task> afterProjects, ArrayList<Task> beforeProjects) {
        if (null != beforeProjects && !beforeProjects.isEmpty() && null != afterProjects && !afterProjects.isEmpty()) {
            afterProjects.each { afterProject ->
                beforeProjects.each { beforeProject ->
                    afterProject.dependsOn(beforeProject)
                }
            }
            return true
        } else {
            return false
        }
    }

    static boolean taskUploadDependsOn(Task uploadTask, ArrayList<Task> beforeProjects) {
        if (null != beforeProjects && !beforeProjects.isEmpty()) {
            beforeProjects.each { beforeProject ->
                uploadTask.dependsOn(beforeProject)
            }
            return true
        } else {
            return false
        }
    }

}
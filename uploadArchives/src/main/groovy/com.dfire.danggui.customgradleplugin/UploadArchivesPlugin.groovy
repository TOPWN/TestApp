package com.dfire.danggui.customgradleplugin

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class UploadArchivesPlugin implements Plugin<Project> {

    public static final String UPDATE_PROPERTY_NAME = 'config.properties'
    public static final String UPLOAD_KEY_PREFIX = 'UPLOAD_TO_NEXUS'
    Project mProject
    Set<Project> allProject
    Task uploadTask
    Queue<Task> uploadTaskQueue

    void apply(Project project) {
        mProject = project
        allProject = project.rootProject.allprojects
        project.afterEvaluate {
            File configFile = checkConfigPropertyExist()
            initUploadTasks(configFile)
            uploadTask = project.task('uploadNexus') << {
                println "Upload to Nexus Completed"
            }
            if (!uploadTaskQueue.empty) {
                Task lastTask = uploadTaskQueue.last()
                uploadTask.dependsOn lastTask
            }
        }
    }

    /**
     * 初始化获取所有待上传project的uploadArchivesTask
     * @return
     */
    def initUploadTasks(File configFile) {
        uploadTaskQueue = new LinkedList<>()
        def props = new Properties()
        props.load(new FileInputStream(configFile))
        Set<Map.Entry<Object, Object>> entrys = props.entrySet()
        entrys.each { entry ->
            String entryKey = entry.getKey().toString()
            if (entryKey.contains(UPLOAD_KEY_PREFIX) && Boolean.valueOf(entry.getValue())) {
                putProjectUploadTask(entryKey)
            }
        }
    }

    /**
     * 根据config.properties中的key过滤出project name,然后获取其中的uploadArchivesTask
     * @param propertyKey
     */
    def putProjectUploadTask(String propertyKey) {
        String projectName = propertyKey.substring(UPLOAD_KEY_PREFIX.length() + 1)
        if (null != allProject) {
            allProject.each { project ->
                if (project.name.equalsIgnoreCase(projectName)) {
                    project.tasks.each { Task task ->
                        if (task.name.contains("uploadArchives")) {
                            //让队列内的task从前至后依次有依赖关系，可以串联执行
                            if (!uploadTaskQueue.empty) {
                                Task lastTask = uploadTaskQueue.last()
                                task.dependsOn lastTask
                            }
                            uploadTaskQueue.offer(task)
                        }
                    }
                }
            }
        }
    }
    /**
     * 校验配置文件是否存在
     */
    def checkConfigPropertyExist() {
        File configFile = getUpdateFile()
        if (!configFile.exists()) {
            throw new GradleException('You need put the config.properties file in project root')
        }
        return configFile
    }

    /**
     * 配置文件
     * @return
     */
    def getUpdateFile() {
        return new File("${mProject.rootDir}/${UPDATE_PROPERTY_NAME}")
    }
}
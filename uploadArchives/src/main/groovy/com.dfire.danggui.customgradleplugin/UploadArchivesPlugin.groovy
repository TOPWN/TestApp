package com.dfire.danggui.customgradleplugin

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class UploadArchivesPlugin implements Plugin<Project> {

    /**
     * 待上传的版本配置文件
     */
    public static final String UPDATE_PROPERTY_NAME = 'config.properties'
    /**
     * 配置文件中各个模块是否需要上传的开关key的前缀
     */
    public static final String UPLOAD_KEY_PREFIX = 'UPLOAD_TO_NEXUS'
    /**
     * 配置文件中是否需要调用该上传task的总开关
     */
    public static final String UPLOAD_NEXUS = 'UPLOAD_NEXUS'
    Project mProject
    Set<Project> allProject
    Task uploadTask
    Queue<Task> uploadTaskQueue
    File configFile
    Properties props

    void apply(Project project) {
        mProject = project
        allProject = project.rootProject.allprojects
        project.afterEvaluate {
            configFile = checkConfigPropertyExist()
            initUploadTasks(configFile)
            uploadTask = project.task('uploadNexus') << {
                //所有任务执行完成后，修改config.properties文件中"UPLOAD_NEXUS"为false
                props.setProperty(UPLOAD_NEXUS, "false")
                props.store(new FileOutputStream(configFile), null)
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
        //顺序读出properties文件中的key
        props = new OrderedProperties()
        props.load(new FileInputStream(configFile))
        Set<String> keys = props.keySet()
        keys.each { key ->
            String value = props.getProperty(key)
            if (key.contains(UPLOAD_KEY_PREFIX) && Boolean.valueOf(value)) {
                putProjectUploadTask(key)
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
                            //任务执行完成后，修改config.properties文件中相应的key为false
                            task.doLast {
                                props.setProperty(propertyKey, "false")
                                props.store(new FileOutputStream(configFile), null)
                            }
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
package com.digua.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project

class DiguaPorjectPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.task("diguaTask")
        .doLast {
            println("测试插件gradle")
        }
    }
}

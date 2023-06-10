plugins {
    kotlin("js")
    id("tz.co.asoft.library")
}

description = "A kotlin/js library for helping in uploading images on web using react"

kotlin {
    js(IR) { browserLib() }
    sourceSets {
        val main by getting {
            dependencies {
                api(projects.symphonyInputImageWeb)
                api(project.dependencies.platform(kotlinw.bom))
                api(projects.cinematicLiveReact)
                api(kotlinw.react)
                api(kotlinw.react.dom)
            }
        }
    }
}
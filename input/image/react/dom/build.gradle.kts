plugins {
    kotlin("js")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform library for headless image input uploads"

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
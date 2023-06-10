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
                api(projects.symphonyInputImageCore)
                api(project.dependencies.platform(kotlinw.bom))
                api(kotlinw.browser)
            }
        }
    }
}
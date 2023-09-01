plugins {
    id("org.jetbrains.compose")
    kotlin("multiplatform")
    id("tz.co.asoft.library")
}

description = "A kotlin/js library for helping in uploading images on web using compose-html"

kotlin {
    js(IR) { browserLib() }
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(compose.html.core)
                api(projects.symphonyInputImageWeb)
                api(libs.cinematic.live.compose)
            }
        }
    }
}
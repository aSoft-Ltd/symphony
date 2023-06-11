plugins {
    id("org.jetbrains.compose")
    kotlin("multiplatform")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform library for helping in uploading images using compose-multiplatform"

kotlin {
    if (Targeting.JVM) jvm { library() }
    if (Targeting.JS) js(IR) { library() }
//    if (Targeting.WASM) wasm { library() }

    val osxTargets = if (Targeting.OSX) osxTargets() else listOf()
//    val ndkTargets = if (Targeting.NDK) ndkTargets() else listOf()
//    val linuxTargets = if (Targeting.LINUX) linuxTargets() else listOf()
//    val mingwTargets = if (Targeting.MINGW) mingwTargets() else listOf()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.cinematicLiveCompose)
                api(compose.runtime)
                api(projects.symphonyInputImageCore)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.kommanderCore)
            }
        }
    }
}
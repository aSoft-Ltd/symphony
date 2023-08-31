plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform library for headless input fields"

kotlin {
    jvm { library() }
    if (Targeting.JS) js(IR) { library() }
//    if (Targeting.WASM) wasm { library() }
    val osxTargets = if (Targeting.OSX) osxTargets() else listOf()
//    val ndkTargets = if (Targeting.NDK) ndkTargets() else listOf()
    val linuxTargets = if (Targeting.LINUX) linuxTargets() else listOf()
//    val mingwTargets = if (Targeting.MINGW) mingwTargets() else listOf()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.symphonyInputCore)
                api(projects.cinematicSceneCore) // because a form is a scene
                api(projects.symphonyTable)
                api(projects.lexiConsole)
            }
        }

        val commonTest by getting {
            dependencies {
                api(libs.kommander.coroutines)
                api(libs.koncurrent.later.coroutines)
                api(projects.symphonyInputChoice)
                api(projects.symphonyInputText)
                api(projects.symphonyInputNumber)
            }
        }
    }
}
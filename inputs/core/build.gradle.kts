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
                api(projects.kevlarCore) // because forms needs submit actions
                api(projects.kaseCore) // becuase forms has states
                api(projects.symphonyCollectionsRenderersString) // because form needs to print the table
                api(projects.cinematicSceneCore) // because a form is a viewmodel
                api(projects.liquidNumber)
                api(kotlinx.serialization.json) // because forms need to serialize
//                api(projects.formatterCore) // because number inputs might need to be formatted
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.kommanderCoroutines)
                implementation(projects.koncurrentLaterCoroutines)
                implementation(projects.koncurrentExecutorsMock)
                implementation(projects.cinematicLiveTest)
            }
        }
    }
}
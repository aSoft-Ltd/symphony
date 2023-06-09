plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform library for headless input phone & email fields"

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
                api(projects.symphonyInputText)
                api(projects.symphonyInputNumber)
                api(projects.symphonyInputChoice)
                api(projects.geoCountries)
                api(projects.identifierComm)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.symphonyInputText)
                implementation(projects.symphonyInputChoice)
                implementation(projects.kommanderCoroutines)
                implementation(projects.koncurrentLaterCoroutines)
                implementation(projects.cinematicLiveTest)
                implementation(projects.koncurrentExecutorsMock)
            }
        }
    }
}
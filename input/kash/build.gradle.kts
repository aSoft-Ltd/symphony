plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform library for headless input money fields"

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
                api(projects.symphonyInputChoice)
                api(projects.symphonyInputNumber)
                api(projects.geoCountries)
                api(projects.kashMoney)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.symphonyInputText)
                implementation(libs.kommander.coroutines)
                implementation(libs.koncurrent.later.coroutines)
                implementation(projects.cinematicLiveTest)
                implementation(libs.koncurrent.executors.mock)
            }
        }
    }
}
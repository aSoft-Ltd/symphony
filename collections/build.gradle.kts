plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
    id("org.jetbrains.dokka")
}

description = "A kotlin multiplatform library"

kotlin {
    jvm { library() }
    js(IR) { library() }
//    if (Targeting.WASM) wasm { library() }
    if (Targeting.OSX) osxTargets() else listOf()
//    if (Targeting.NDK) ndkTargets() else listOf()
    if (Targeting.LINUX) linuxTargets() else listOf()
//    if (Targeting.MINGW) mingwTargets() else listOf()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.keep.api)
                api(libs.cinematic.scene.core)
                api(projects.symphonyInputText)
                api(projects.symphonyTable)
                api(projects.symphonyList)
            }
        }
    }
}
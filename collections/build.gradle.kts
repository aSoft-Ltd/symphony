plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
    id("org.jetbrains.dokka")
}

description = "A kotlin multiplatform library"

kotlin {
    if (Targeting.JVM) jvm { library() }
    if (Targeting.JS) js(IR) { library() }
    if (Targeting.WASM) wasmJs { library() }
    if (Targeting.OSX) (iosTargets() + macOsTargets())
    if (Targeting.LINUX) linuxTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.cinematic.scene.core)
                api(projects.symphonyInputText)
                api(projects.symphonyTable)
                api(projects.symphonyList)
                api(libs.keep.api)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kommander.core)
                implementation(kotlinx.coroutines.test)
            }
        }

        if (Targeting.JVM) jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
        }
    }
}
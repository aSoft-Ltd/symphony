plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform library for headless input fields"

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xenable-suspend-function-exporting")
    }
    if (Targeting.JVM) jvm { library() }
    if (Targeting.JS) js(IR) { library() }
    if (Targeting.WASM) wasmJs { library() }
    if (Targeting.WASM) wasmWasi { library() }
    if (Targeting.OSX) (iosTargets() + macOsTargets())
    if (Targeting.LINUX) linuxTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.symphonyInputCore)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kommander.core)
                implementation(libs.cinematic.live.test)
            }
        }

        if (Targeting.JVM) jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
        }
    }
}
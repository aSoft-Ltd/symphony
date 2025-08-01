plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform library for headless spreadsheet"

kotlin {
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
                api(projects.symphonyInputText)?.because("We need to capture form labels")
                api(projects.symphonyInputNumber)?.because("We need to capture amounts")
//                api(libs.krono.fields)?.because("We need to capture dates")
                api(projects.symphonyInputChoice)?.because("We need to choose if our rows are expandable or collapsed")
                api(libs.cinematic.live.kollections)
                api(libs.neat.validation)
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

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
        commonMain.dependencies {
            api(projects.symphonyVisibility)
            api(libs.kotlinx.exports)
            api(libs.kase.core)
            api(libs.cinematic.live.core)
            api(libs.neat.validation)
            api(libs.kevlar.core)
            api(libs.lexi.console)
            api(kotlinx.serialization.core)
        }

        commonMain.dependencies {
            implementation(kotlin("test"))
            implementation(kotlinx.coroutines.test)
            implementation(libs.kommander.core)
        }

        if (Targeting.JVM) jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
        }
    }
}

import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform library for representing headless collection based ui such as lists, tables and grids"

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xenable-suspend-function-exporting")
    }
    if (Targeting.JVM) jvm { library() }
    if (Targeting.JS) js(IR) { library() }
    if (Targeting.WASM) wasmJs { library() }
    if (Targeting.OSX) (iosTargets() + macOsTargets())
    if (Targeting.LINUX) linuxTargets()

    sourceSets {
        commonMain.dependencies {
            api(libs.kevlar.core)
            api(projects.symphonySelector)
        }

        commonTest.dependencies {
            implementation(libs.kommander.coroutines)
        }

        if (Targeting.JVM) jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
        }
    }
}

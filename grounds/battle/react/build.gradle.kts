plugins {
    kotlin("js")
    id("tz.co.asoft.library")
}

description = "A sample for react router"

kotlin {
    js(IR) {
        moduleName = project.name
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
                outputFileName = "main.bundle.js"
            }
        }
        generateTypeScriptDefinitions()
        binaries.executable()
    }

    sourceSets {
        val main by getting {
            dependencies {
                implementation(projects.symphonyInputFile)
                implementation(project.dependencies.platform(kotlinw.bom))
                implementation(projects.cinematicLiveReact)
                implementation(kotlinw.react)
                implementation(kotlinw.react.dom.new)
            }
        }
    }
}
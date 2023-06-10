pluginManagement {
    enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    dependencyResolutionManagement {
        versionCatalogs {
            file("../versions/gradle/versions").listFiles().map {
                it.nameWithoutExtension to it.absolutePath
            }.forEach { (name, path) ->
                create(name) { from(files(path)) }
            }
        }
    }
}

fun includeRoot(name: String, path: String) {
    include(":$name")
    project(":$name").projectDir = File(path)
}

fun includeSubs(base: String, path: String = base, vararg subs: String) {
    subs.forEach {
        include(":$base-$it")
        project(":$base-$it").projectDir = File("$path/$it")
    }
}

rootProject.name = "symphony"

includeBuild("../able")

// dependencies for symphony-input-core
//includeSubs("functions", "../functions", "core")
includeSubs("kommander", "../kommander", "core", "coroutines")
includeSubs("liquid", "../liquid", "number")
includeSubs("kollections", "../kollections", "atomic", "interoperable")
includeSubs("koncurrent-executors", "../koncurrent/executors", "core", "coroutines", "mock")
includeSubs("koncurrent-later", "../koncurrent/later", "core", "coroutines", "test")
includeSubs("kevlar", "../kevlar", "core")
includeSubs("kase", "../kase", "core")
includeSubs("keep", "../keep", "api", "file", "mock", "test")
includeSubs("lexi", "../lexi", "api", "console")
includeSubs("lexi-test", "../lexi/test", "android")
includeSubs("krest", "../krest", "core")
includeSubs("cinematic-live", "../cinematic/live", "core", "coroutines", "test", "kollections", "react", "compose")
includeSubs("cinematic-scene", "../cinematic/scene", "core")

//dependencies-for symphony-input-file
includeSubs("epsilon", "../epsilon", "core", "file")

//dependencies-for symphony-input-krono
includeSubs("krono", "../krono", "api", "kotlinx")

//dependencies-for symphony-input-kash
includeBuild("../kash/currency-generator")
includeSubs("kash", "../kash", "currency", "money")

//dependencies-for symphony-input-geo
includeBuild("../geo/geo-generator")
includeSubs("geo", "../geo", "core", "countries")

// dependencies for symphony-input-identifier
includeSubs("identifier", "../identifier", "core", "comm")

// submodules
includeSubs("symphony", ".", "paginator", "selector", "actions", "table", "list", "collections")
includeSubs("symphony-input", "input", "core", "form", "text", "number", "choice", "list", "file", "identifier", "krono", "geo", "kash", "dialog")
includeSubs("symphony-input-image", "input/image", "core", "web")
includeSubs("symphony-input-image-react", "input/image/react", "core", "dom", "native")
includeSubs("symphony-input-image-compose", "input/image/compose", "core", "html")

// grounds
includeSubs("grounds-battle", "grounds/battle", "react")
includeSubs("grounds-battle-compose", "grounds/battle/compose", "html")
pluginManagement {
    includeBuild("../build-logic")
}

plugins {
    id("multimodule")
}

fun includeSubs(base: String, path: String = base, vararg subs: String) {
    subs.forEach {
        include(":$base-$it")
        project(":$base-$it").projectDir = File("$path/$it")
    }
}

listOf(
    "kommander", "kollections", "kevlar", "kase",
    "keep", "lexi", "neat", "cinematic", "koncurrent", "kotlinx-interoperable"
).forEach {
    includeBuild("../$it")
}

rootProject.name = "symphony"

// submodules
includeSubs("symphony", ".", "paginator", "selector", "actions", "table", "list", "collections", "visibility")
includeSubs("symphony-input", "input", "core", "text", "number", "choice", "dialog")

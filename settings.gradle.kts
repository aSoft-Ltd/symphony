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
    "kommander", "kevlar", "kase", "nation", "sim",
    "keep", "lexi", "neat", "cinematic", "kotlinx-interoperable",
).forEach {
    includeBuild("../$it")
}

rootProject.name = "symphony"

// submodules
includeSubs("symphony", ".", "pagination", "paginator", "selector", "actions", "table", "list", "collections", "visibility", "test")
includeSubs("symphony-input", "input", "core", "text", "number", "choice", "dialog", "sheet", "finance", "phone")
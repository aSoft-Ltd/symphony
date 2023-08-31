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
	"keep", "lexi", "neat", "cinematic", "koncurrent",
).forEach {
	includeBuild("../$it")
}

rootProject.name = "symphony"

// submodules
includeSubs("symphony", ".", "paginator", "selector", "actions", "table", "list", "collections")
includeSubs("symphony-input", "input", "core", "text", "number", "choice", "list", "dialog")

// grounds
// includeSubs("grounds-battle", "grounds/battle", "react")
// includeSubs("grounds-battle-compose", "grounds/battle/compose", "core", "html")

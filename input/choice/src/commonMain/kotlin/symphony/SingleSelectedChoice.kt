package symphony

data class SingleSelectedChoice<out T>(
    val item: T,
    val option: Option
)
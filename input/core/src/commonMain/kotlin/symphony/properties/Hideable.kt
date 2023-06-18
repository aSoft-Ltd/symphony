package symphony.properties

interface Hideable {
    val hidden: Boolean
    fun show(show: Boolean? = true)
    fun hide(hide: Boolean? = true)
}
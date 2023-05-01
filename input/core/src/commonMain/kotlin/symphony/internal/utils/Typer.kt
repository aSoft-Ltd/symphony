package symphony.internal.utils

import symphony.properties.Settable
import symphony.properties.Typeable

class Typer(
    private val old: String?,
    private val setter: Settable<String>
) : Typeable {
    override fun type(text: String) {
        for (i in 0..text.lastIndex) {
            setter.set((old ?: "") + text.substring(0..i))
        }
    }
}
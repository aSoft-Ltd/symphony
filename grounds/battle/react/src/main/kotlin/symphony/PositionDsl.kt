@file:Suppress("NOTHING_TO_INLINE")

package symphony

import js.core.jso
import web.html.Image
import web.uievents.MouseEvent

external interface Position {
    var x: Int
    var y: Int
}

inline fun Position(x: Number = 0, y: Number = 0) = jso<Position> {
    this.x = x.toInt()
    this.y = y.toInt()
}

inline fun Position.update(e: MouseEvent) {
    x = e.clientX
    y = e.clientY
}

inline fun MouseEvent.toPosition() = jso<Position> {
    x = clientX
    y = clientY
}

operator fun Position.plus(other: Position): Position {
    val pos = jso<Position>()
    pos.x = x + other.x
    pos.y = y + other.y
    return pos
}

operator fun Position.minus(other: Position): Position {
    val pos = jso<Position>()
    pos.x = x - other.x
    pos.y = y - other.y
    return pos
}

inline val Image.size get() = Position(width, height)

operator fun Position.div(number: Number) = Position(
    x = x.toDouble() / number.toDouble(),
    y = y.toDouble() / number.toDouble()
)

operator fun Position.times(number: Number) = Position(
    x = x.toDouble() * number.toDouble(),
    y = y.toDouble() * number.toDouble()
)
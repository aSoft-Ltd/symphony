package symphony

import js.core.jso
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import web.cssom.AlignItems
import web.cssom.Display
import web.cssom.JustifyContent
import web.cssom.pct

internal val ImageUploaderPlaceholder = FC<Props> {
    div {
        style = jso {
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = AlignItems.center
            width = 100.pct
            height = 100.pct
        }
        span { +"Upload image" }
    }
}
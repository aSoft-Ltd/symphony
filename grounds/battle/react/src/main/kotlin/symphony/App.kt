@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.watchAsState
import epsilon.BrowserBlob
import epsilon.FileBlob
import epsilon.fileBlobOf
import js.core.asList
import js.core.jso
import react.FC
import react.Fragment
import react.Props
import react.ReactNode
import react.create
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.canvas
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useEffect
import react.useRef
import symphony.ImageUploaderScene.PendingState
import symphony.ImageUploaderScene.RefiningState
import web.canvas.CanvasRenderingContext2D
import web.canvas.RenderingContextId
import web.cssom.AlignItems
import web.cssom.Auto
import web.cssom.Color
import web.cssom.Cursor
import web.cssom.Display
import web.cssom.JustifyContent
import web.cssom.LineStyle.Companion.solid
import web.cssom.None
import web.cssom.Outline
import web.cssom.array
import web.cssom.fr
import web.cssom.pct
import web.cssom.px
import web.file.FileList
import web.html.HTMLCanvasElement
import web.html.HTMLDivElement
import web.html.HTMLInputElement
import web.html.Image
import web.html.InputType
import web.timers.Timeout
import web.timers.clearInterval
import web.timers.setInterval
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration.Companion.milliseconds

val FileUploaderApp = FC<Props> {
    h1 { +"Image Uploader" }

    div {
        style = jso {
            display = Display.grid
            gridTemplateColumns = array(1.fr, 1.fr, 1.fr, 1.fr)
        }

        div {
            style = jso { height = 200.px }
            SingleFileUploader {
                scene = ImageUploaderScene()
            }
        }

        div {
            style = jso { height = 300.px }
            SingleFileUploader {
                scene = ImageUploaderScene()
                placeholder = span.create { +"Drag image here to upload" }
                color = "blue"
                onSave = {
                    val img = it.toFileBlob("jane.png").getOrThrow("Couldn't convert to image")
                    kotlinx.browser.window.location.href = img.path
                }
                save = button.create {
                    style = jso {
                        width = 100.pct
                        height = 50.px
                    }
                    +"Upload"
                }
            }
        }

        div {
            style = jso { height = 400.px }
            SingleFileUploader {
                scene = ImageUploaderScene()
                placeholder = div.create {
                    style = jso { display = Display.flex }
                    h1 { +"Upload Image" }
                }
                color = "green"
                save = Fragment.create()
            }
        }

        div {
            style = jso { height = 500.px }
            SingleFileUploader {
                scene = ImageUploaderScene()
                placeholder = div.create { +"Upload Image" }
                color = "green"
            }
        }
    }
}

private const val COLOR = "gray"

external interface SingleFileUploaderProps : Props {
    var scene: ImageUploaderScene
    var placeholder: ReactNode?
    var save: ReactNode?
    var onSave: ((BrowserBlob) -> Unit)?
    var color: String?
}

private fun FileBlob.toImage() = Image().apply { src = path }

private fun ImageUploaderScene.selectFirst(files: FileList?) {
    val file = files?.asList()?.firstOrNull() ?: return
    select(fileBlobOf(file.unsafeCast<org.w3c.files.File>()))
}

val SingleFileUploader = FC<SingleFileUploaderProps> { props ->
    val scene = props.scene
    val state = scene.state.watchAsState()
    val canvasRef = useRef<HTMLCanvasElement>()
    val inputRef = useRef<HTMLInputElement>()
    val saveRef = useRef<HTMLDivElement>()
    val primaryColor = props.color ?: COLOR
    val placeholder = props.placeholder ?: Placeholder.create()
    val save = props.save ?: Save.create()

    useEffect(state, canvasRef.current) {
        val fileBlobAsImage = (state as? RefiningState)?.image ?: return@useEffect
        val canvas = canvasRef.current ?: return@useEffect
        val parent = canvas.parentElement ?: return@useEffect
        val saveElement = saveRef.current ?: return@useEffect
        val canvasWidth = parent.offsetWidth
        val canvasHeight = parent.offsetHeight - saveElement.offsetHeight
        canvas.width = parent.offsetWidth
        canvas.height = canvasHeight
        val context = canvas.getContext(RenderingContextId.canvas) ?: return@useEffect
        val image = fileBlobAsImage.toImage()
        val full = Position(canvasWidth, canvasHeight)

        var renderer = 0.unsafeCast<Timeout>()

        image.onload = {
            renderer = initialize(canvas, context, image, full, primaryColor)
        }
        cleanup { clearInterval(renderer) }
    }

    div {
        style = jso {
            display = Display.block
            outline = Outline(width = 2.px, style = solid, color = Color(primaryColor))
//            border = Border(width = 2.px, style = solid, color = Color(primaryColor))
//            padding = Padding(horizontal = 2.px, vertical = 2.px)
            width = 100.pct
            height = 100.pct
            cursor = Cursor.pointer
        }

        onDrop = {
            it.preventDefault()
            scene.selectFirst(it.dataTransfer.files)
        }

        onDragOver = { it.preventDefault() }

        onClick = {
            if (state is PendingState) {
                inputRef.current?.click()
            }
        }

        onDoubleClick = { inputRef.current?.click() }

        input {
            ref = inputRef
            type = InputType.file
            style = jso { display = None.none }
            onChange = { scene.selectFirst(it.target.files) }
        }

        div {
            style = jso {
                display = Display.grid
                height = 100.pct
                gridTemplateColumns = array(1.fr)
                gridTemplateRows = array(1.fr, Auto.auto)
            }
            when (state) {
                is PendingState -> child(placeholder)

                is RefiningState -> canvas {
                    ref = canvasRef
                    style = jso {
                        width = 100.pct
                        height = 100.pct
                        backgroundColor = Color(primaryColor)
                        cursor = Cursor.move
                    }
                }
            }
            div {
                ref = saveRef
                style = jso { width = 100.pct }
                onClick = {
                    canvasRef.current?.toBlob({
                        val b = it?.unsafeCast<org.w3c.files.Blob>() ?: return@toBlob
                        val blob = BrowserBlob(b)
                        props.onSave?.invoke(blob)
                    }, "image/png", 1)
                }
                child(save)
            }
        }
    }
}


private val Placeholder = FC<Props> {
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

private val Save = FC<Props> {
    button {
        style = jso {
            width = 100.pct
        }
        +"Save"
    }
}
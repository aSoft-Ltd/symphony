@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.watchAsState
import epsilon.FileBlob
import epsilon.fileBlobOf
import js.core.asList
import js.core.jso
import react.FC
import react.Props
import react.ReactNode
import react.create
import react.dom.html.ReactHTML.canvas
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.useEffect
import react.useRef
import symphony.ImageUploaderScene.PendingState
import symphony.ImageUploaderScene.RefiningState
import web.canvas.CanvasRenderingContext2D
import web.canvas.RenderingContextId
import web.cssom.Border
import web.cssom.Color
import web.cssom.Display
import web.cssom.LineStyle.Companion.solid
import web.cssom.Padding
import web.cssom.pct
import web.cssom.px
import web.html.HTMLCanvasElement
import web.html.Image
import web.html.InputType
import web.timers.Timeout
import web.timers.clearInterval
import web.timers.setInterval
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration.Companion.milliseconds

val FileUploaderApp = FC<Props> {
    h1 {
        +"Image Uploader"
    }

    SingleFileUploader {
        scene = ImageUploaderScene()
        placeholder = h1.create { +"Upload" }
        widthInPx = 120
        heightInPx = 100
    }
}

private const val WIDTH = 300.0
private const val HEIGHT = 300.0
private const val COLOR = "gray"

external interface SingleFileUploaderProps : Props {
    var scene: ImageUploaderScene
    var placeholder: ReactNode?
    var widthInPx: Int?
    var heightInPx: Int?
    var color: String?
}

fun FileBlob.toImage() = Image().apply { src = path }

fun fit(src: Position, dst: Position): Position {
    val hRatio = dst.x.toDouble() / src.x
    val vRatio = dst.y.toDouble() / src.y
    val ratio = min(hRatio, vRatio)
    return jso {
        x = (src.x * ratio).toInt()
        y = (src.y * ratio).toInt()
    }
}

private fun initialize(
    canvas: HTMLCanvasElement,
    context: CanvasRenderingContext2D,
    full: Position,
    image: Image
): Timeout {
    var dragging = false
    val dragStart = Position()
    var dragRef = Position()

    var scale = 1.0
    val size = fit(image.size, full)

    val startPoint = Position(
        x = (full.x - size.x) / 2,
        y = (full.y - size.y) / 2
    )

    var imageAnchor = startPoint

    canvas.onmousedown = {
        dragging = true
        dragStart.update(it)
        dragRef = imageAnchor
    }

    canvas.onwheel = {
        it.preventDefault()
        scale += it.deltaY * -0.01

        // Restrict scale
        scale = min(max(0.125, scale), 4.0)
        imageAnchor = startPoint + size * (1 - scale) / 2
    }

    canvas.onmousemove = {
        if (dragging) {
            val dragEnd = it.toPosition()
            val dDrag = dragEnd - dragStart
            imageAnchor = dragRef + dDrag
        }
    }
    canvas.onmouseup = { dragging = false }

    return setInterval(10.milliseconds) { // 100 frames per seconds
        context.clearRect(0, 0, full.x, full.y)
        val s = size * scale
        context.drawImage(image, imageAnchor.x, imageAnchor.y, s.x, s.y)
    }
}


val SingleFileUploader = FC<SingleFileUploaderProps> { props ->
    val scene = props.scene
    val state = scene.state.watchAsState()
    val canvasRef = useRef<HTMLCanvasElement>()
    val fullWidth = props.widthInPx ?: WIDTH
    val fullHeight = props.heightInPx ?: HEIGHT
    val primaryColor = props.color ?: COLOR

    useEffect(state, canvasRef.current) {
        val fileBlobAsImage = (state as? RefiningState)?.image ?: return@useEffect
        val canvas = canvasRef.current ?: return@useEffect
        canvas.width = fullWidth.toInt()
        canvas.height = fullHeight.toInt()
        val context = canvas.getContext(RenderingContextId.canvas) ?: return@useEffect
        val image = fileBlobAsImage.toImage()
        val full = Position(fullWidth, fullHeight)

        var renderer = 0.unsafeCast<Timeout>()

        image.onload = {
            renderer = initialize(canvas, context, full, image)
        }
        cleanup { clearInterval(renderer) }
    }

    div {
        style = jso {
            display = Display.block
            border = Border(width = 2.px, style = solid, color = Color(primaryColor))
            padding = Padding(horizontal = 2.px, vertical = 2.px)
            width = fullWidth.px
            height = fullHeight.px
        }

        onDrop = {
            it.preventDefault()
            val file = it.dataTransfer.files.asList().firstOrNull()
            if (file != null) {
                scene.select(fileBlobOf(file.unsafeCast<org.w3c.files.File>()))
            }
        }

        onDragOver = { it.preventDefault() }

        when (state) {
            is PendingState -> {
                input {
                    type = InputType.file
                    style = jso {
                        display = Display.inline
                    }
                    onChange = {
                        val file = it.target.files?.asList()?.firstOrNull()
                        if (file != null) {
                            scene.select(fileBlobOf(file.unsafeCast<org.w3c.files.File>()))
                        }
                    }
                }
            }

            is RefiningState -> canvas {
                ref = canvasRef
                style = jso {
                    backgroundColor = Color(primaryColor)
                    width = 100.pct
                    height = 100.pct
                }
            }
        }
    }
}
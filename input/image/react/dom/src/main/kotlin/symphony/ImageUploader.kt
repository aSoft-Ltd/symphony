@file:Suppress("NOTHING_TO_INLINE", "NON_EXPORTABLE_TYPE")

package symphony

import cinematic.watchAsState
import epsilon.BrowserBlob
import js.core.jso
import org.w3c.files.Blob
import react.ChildrenBuilder
import react.FC
import react.Fragment
import react.Props
import react.ReactNode
import react.create
import react.dom.html.ReactHTML.canvas
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.useEffect
import react.useRef
import web.canvas.RenderingContextId
import web.cssom.Auto
import web.cssom.Color
import web.cssom.Cursor
import web.cssom.Display
import web.cssom.LineStyle.Companion.solid
import web.cssom.None
import web.cssom.Outline
import web.cssom.array
import web.cssom.fr
import web.cssom.pct
import web.cssom.px
import web.html.HTMLCanvasElement
import web.html.HTMLDivElement
import web.html.HTMLInputElement
import web.html.InputType
import web.timers.Timeout
import web.timers.clearInterval

private const val COLOR = "gray"
private const val NAME = "ImageUploader"

@JsExport
external interface ImageUploaderProps : Props {
    var scene: ImageViewerUploader
    var placeholder: ReactNode?
    var save: ReactNode?
    var onSave: ((BrowserBlob) -> Unit)?
    var color: String?
}

@JsExport
@JsName(NAME)
val InternalImageUploader = FC<ImageUploaderProps>(NAME) { props ->
    val scene = props.scene
    val state = scene.state.watchAsState()
    val canvasRef = useRef<HTMLCanvasElement>()
    val inputRef = useRef<HTMLInputElement>()
    val saveRef = useRef<HTMLDivElement>()
    val primaryColor = props.color ?: COLOR
    val placeholder = props.placeholder ?: ImageUploaderPlaceholder.create()
    val save = props.save ?: ImageUploaderSave.create()

    useEffect(state, canvasRef.current) {
        val fileBlobAsImage = (state as? EditingImage)?.image ?: return@useEffect
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
            scene.editFirst(it.dataTransfer.files)
        }

        onDragOver = { it.preventDefault() }

        onClick = {
            if (state is AwaitingImage) {
                inputRef.current?.click()
            }
        }

        onDoubleClick = { inputRef.current?.click() }

        input {
            ref = inputRef
            type = InputType.file
            style = jso { display = None.none }
            onChange = { scene.editFirst(it.target.files) }
        }

        div {
            style = jso {
                display = Display.grid
                height = 100.pct
                gridTemplateColumns = array(1.fr)
                gridTemplateRows = array(1.fr, Auto.auto)
            }
            when (state) {
                is AwaitingImage -> child(placeholder)

                is EditingImage -> canvas {
                    ref = canvasRef
                    style = jso {
                        width = 100.pct
                        height = 100.pct
                        backgroundColor = Color(primaryColor)
                        cursor = Cursor.move
                    }
                }

                is LoadingToEditImage -> TODO()
                is UploadingImage -> TODO()
                is ViewingImage -> TODO()
            }

            div {
                ref = saveRef
                style = jso { width = 100.pct }
                onClick = {
                    canvasRef.current?.toBlob({
                        val b = it?.unsafeCast<Blob>() ?: return@toBlob
                        val blob = BrowserBlob(b)
                        props.onSave?.invoke(blob)
                    }, "image/png", 1)
                }
                child(save)
            }
        }
    }
}

inline fun ChildrenBuilder.ImageUploader(
    scene: ImageViewerUploader,
    noinline placeholder: (ChildrenBuilder.() -> Unit)? = null,
    noinline save: (ChildrenBuilder.() -> Unit)? = null,
    noinline onSave: ((BrowserBlob) -> Unit)? = null,
    color: String? = null
) = InternalImageUploader {
    this.scene = scene
    this.placeholder = if (placeholder != null) Fragment.create { placeholder() } else null
    this.save = if (save != null) Fragment.create { save() } else null
    this.onSave = onSave
    this.color = color
}
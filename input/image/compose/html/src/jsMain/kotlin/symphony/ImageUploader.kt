package symphony

import androidx.compose.runtime.Composable
import cinematic.watchAsState
import epsilon.BrowserBlob
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.gridTemplateColumns
import org.jetbrains.compose.web.css.gridTemplateRows
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.outline
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Canvas
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.FileInput

@Composable
fun ImageUploader(
    scene: ImageViewerUploader,
    placeholder: (@Composable () -> Unit)? = null,
    save: (@Composable () -> Unit)? = null,
    onSave: ((BrowserBlob) -> Unit)? = null,
    color: String? = null
) {

    val state = scene.state.watchAsState()
    val primaryColor = Color(color ?: "gray")

    Div({
        style {
            display(DisplayStyle.Flex)
            outline(width = 2.px, style = "solid", color = primaryColor)
            width(100.percent)
            height(100.percent)
            cursor("pointer")
        }

        onDrop {
            it.preventDefault()
            scene.editFirst(it.dataTransfer?.files)
        }

        onDragOver { it.preventDefault() }

        onClick {
            if (state is AwaitingImage) {
                // TODO: click the input here
            }
        }

        onDoubleClick { /* TODO: click the input here */ }

    }) {
        FileInput {
            style { display(DisplayStyle.None) }
            ref {
                onDispose {}
            }
            onChange { scene.editFirst(it.target.files) }
        }

        Div({
            style {
                display(DisplayStyle.Grid)
                height(100.percent)
                gridTemplateColumns("1fr")
                gridTemplateRows("1fr auto")
            }
        }) {
            when (state) {
                is AwaitingImage -> placeholder?.invoke() ?: ImageUploaderPlaceholder()
                is EditingImage -> Canvas({
                    style {
                        width(100.percent)
                        height(100.percent)
                        backgroundColor(primaryColor)
                        cursor("move")
                    }
                }) { }

                is LoadingToEditImage -> TODO()
                is UploadingImage -> TODO()
                is ViewingImage -> TODO()
            }
        }

        Div({
            style { width(100.percent) }
            onClick {
                console.log("Print do save")
            }
        }) { save?.invoke() ?: ImageUploaderSave() }
    }
}
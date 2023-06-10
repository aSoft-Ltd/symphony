package symphony

import androidx.compose.runtime.Composable
import epsilon.BrowserBlob
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun ImageUploader(
    scene: ImageViewerUploader,
    placeholder: (@Composable () -> Unit)? = null,
    save: (@Composable () -> Unit)? = null,
    onSave: ((BrowserBlob) -> Unit)? = null,
    color: String? = null
) {
    Div {
        Text("Testing sstuff")
    }
}
package symphony

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ImageUploaderApp() {
    Canvas(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        drawCircle(Color.Red)
    }
}
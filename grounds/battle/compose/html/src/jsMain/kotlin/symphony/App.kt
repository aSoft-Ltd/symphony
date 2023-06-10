@file:Suppress("NOTHING_TO_INLINE")

package symphony

import androidx.compose.runtime.Composable
import js.core.jso
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import web.cssom.Display
import web.cssom.array
import web.cssom.fr
import web.cssom.pct
import web.cssom.px

@Composable
fun FileUploaderApp() {
    Div { Text("Works") }
}

//val FileUploaderAppTmp = FC<Props> {
//    h1 { +"Image Uploader" }
//
//    div {
//        style = jso {
//            display = Display.grid
//            gridTemplateColumns = array(1.fr, 1.fr, 1.fr, 1.fr)
//        }
//
//        div {
//            style = jso { height = 200.px }
//            InternalImageUploader {
//                scene = ImageViewerUploader()
//            }
//        }
//
//        div {
//            style = jso { height = 300.px }
//            ImageUploader(
//                scene = ImageViewerUploader(),
//                placeholder = { span { +"Drag image here to upload" } },
//                onSave = {
//                    val img = it.toFileBlob("jane.png").getOrThrow("Couldn't convert to image")
//                    kotlinx.browser.window.location.href = img.path
//                },
//                save = {
//                    button {
//                        style = jso {
//                            width = 100.pct
//                            height = 50.px
//                        }
//                        +"Upload"
//                    }
//                }
//            )
//        }
//
//        div {
//            style = jso { height = 400.px }
//            InternalImageUploader {
//                scene = ImageViewerUploader()
//                placeholder = div.create {
//                    style = jso { display = Display.flex }
//                    h1 { +"Upload Image" }
//                }
//                color = "green"
//                save = Fragment.create()
//            }
//        }
//
//        div {
//            style = jso { height = 500.px }
//            InternalImageUploader {
//                scene = ImageViewerUploader()
//                placeholder = div.create { +"Upload Image" }
//                color = "green"
//            }
//        }
//    }
//}
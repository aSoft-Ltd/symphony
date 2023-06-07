package symphony

import js.core.asList
import js.core.jso
import web.file.File
import react.FC
import react.Props
import react.dom.html.ReactHTML.canvas
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.useState
import web.cssom.Color
import web.html.InputType

val FileUploaderApp = FC<Props> {
    h1 {
        +"File Uploader"
    }

    SingleFileUploader {}
}

const val WIDTH = 300.0
const val HEIGHT = 300.0

val SingleFileUploader = FC<Props> {
    val (images, setImages) = useState(emptyList<File>())

    input {
        type = InputType.file
        multiple = true
        onChange = {
            setImages(it.target.files?.asList() ?: emptyList())
        }
    }

    div {
        for (image in images) canvas {
            style = jso {
                backgroundColor = Color("black")
            }
            width = WIDTH
            height = HEIGHT
        }
    }
}
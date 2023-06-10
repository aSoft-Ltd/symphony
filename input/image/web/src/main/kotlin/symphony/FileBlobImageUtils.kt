package symphony

import epsilon.FileBlob
import epsilon.fileBlobOf
import js.core.asList
import web.file.FileList
import web.html.Image

fun FileBlob.toImage() = Image().apply { src = path }

fun ImageViewerUploader.editFirst(files: FileList?) {
    val file = files?.asList()?.firstOrNull() ?: return
    edit(fileBlobOf(file.unsafeCast<org.w3c.files.File>()))
}
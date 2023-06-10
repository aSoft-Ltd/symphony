@file:JsExport

package symphony

import cinematic.Live
import epsilon.FileBlob
import kollections.JsExport
import koncurrent.Later

interface ImageViewerUploader {
    val state: Live<ImageViewerUploaderState>
    val uploader: ((FileBlob) -> Later<Any?>)?
    fun view(url: String)
    fun edit(image: FileBlob)
}
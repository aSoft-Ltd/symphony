package symphony.internal

import cinematic.mutableLiveOf
import epsilon.FileBlob
import koncurrent.Later
import symphony.EditingImage
import symphony.ImageViewerUploader
import symphony.ImageViewerUploaderState
import symphony.ViewingImage

internal class ImageViewerUploaderImpl(
    start: ImageViewerUploaderState,
    override val uploader: ((FileBlob) -> Later<Any?>)? = null
) : ImageViewerUploader {
    override val state = mutableLiveOf(start)

    override fun view(url: String) {
        state.value = ViewingImage(url)
    }

    override fun edit(image: FileBlob) {
        state.value = EditingImage(image)
    }
}
@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import epsilon.FileBlob
import symphony.properties.Clearable
import symphony.properties.Mutable
import symphony.properties.Requireble
import symphony.properties.Settable
import symphony.validation.Validateable
import kotlin.js.JsExport

interface SingleFileInputField : CommonInputProperties, LiveData<FileBlob>, Settable<FileBlob>, Validateable<FileBlob>, Clearable
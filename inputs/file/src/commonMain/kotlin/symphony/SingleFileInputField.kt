@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import epsilon.FileBlob
import symphony.properties.Clearable
import symphony.properties.Hintable
import symphony.properties.Labeled
import symphony.properties.Mutability
import symphony.properties.Requireble
import symphony.properties.Settable
import symphony.validation.Validateable
import kotlin.js.JsExport

interface SingleFileInputField : Labeled, Hintable, Mutability, Requireble, SerializableLiveData<FileBlob>, Settable<FileBlob>, Validateable<FileBlob>, Clearable
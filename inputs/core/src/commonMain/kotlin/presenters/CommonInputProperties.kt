@file:JsExport

package presenters

import presenters.properties.Clearable
import presenters.properties.Hintable
import presenters.properties.Labeled
import presenters.properties.Mutability
import presenters.properties.Requireble
import kotlin.js.JsExport

@Deprecated("use symphony")
interface CommonInputProperties : Labeled, Hintable, Mutability, Requireble, Clearable
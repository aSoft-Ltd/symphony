@file:JsExport

package symphony

import symphony.properties.Clearable
import symphony.properties.Hintable
import symphony.properties.Labeled
import symphony.properties.Mutability
import symphony.properties.Requireble
import kotlin.js.JsExport

interface CommonInputProperties : Labeled, Hintable, Mutability, Requireble, Clearable
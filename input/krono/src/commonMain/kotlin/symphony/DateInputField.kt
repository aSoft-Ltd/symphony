@file:JsExport

package symphony

import krono.LocalDate
import symphony.properties.Typeable
import kotlin.js.JsExport

interface DateInputField : TransformingInputField<String, LocalDate>, DateOutputField, Typeable
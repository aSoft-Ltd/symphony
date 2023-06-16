@file:JsExport

package symphony

import krono.LocalDate
import symphony.properties.Bounded
import symphony.properties.Patterned
import kotlin.js.JsExport

interface DateOutputField : InputField, LiveDataFormatted<String, LocalDate>, Bounded<LocalDate>, Patterned
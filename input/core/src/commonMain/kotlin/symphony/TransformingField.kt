@file:JsExport

package symphony

import symphony.properties.Settable
import kotlinx.JsExport

interface TransformingField<I, O> : Field<O, TransState<I, O>>, Settable<I>, TransState<I, O>
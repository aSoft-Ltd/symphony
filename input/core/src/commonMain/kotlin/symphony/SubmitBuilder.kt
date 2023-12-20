@file:OptIn(ExperimentalTypeInference::class)

package symphony

import kotlin.experimental.ExperimentalTypeInference

//typealias SubmitBuilder<P, R> = @receiver:BuilderInference SubmitActionsBuilder<P, R>.() -> SubmitActionsBuilder.Finalizer
typealias SubmitBuilder<P, R> = SubmitActionsBuilder<P, R>.() -> SubmitActionsBuilder.Finalizer
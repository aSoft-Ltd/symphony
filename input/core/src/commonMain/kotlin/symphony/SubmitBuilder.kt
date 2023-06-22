package symphony

typealias SubmitBuilder<P, R> = SubmitActionsBuilder<P, R>.() -> SubmitActionsBuilder.Finalizer
package symphony

fun <R, P : Any, F : Fields<P>, I : Input<F>> I.toForm(
    heading: String,
    details: String,
    config: SubmitConfig,
    initializer: SubmitBuilder<P, R>
): Form<R, P, F, I> = Form(heading, details, this, config, initializer)

fun <R, P : Any, F : Fields<P>> F.toForm(
    heading: String,
    details: String,
    config: SubmitConfig,
    builder: SubmitBuilder<P, R>
): Form<R, P, F, Input<F>> = toInput(
    hidden = false,
    config = config,
    builder = builder,
).toForm(heading, details, config, builder)
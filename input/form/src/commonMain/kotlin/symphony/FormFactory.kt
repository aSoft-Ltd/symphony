package symphony

fun <R, P, F : Fields<P>, I : Input<F>> I.toForm(
    heading: String,
    details: String,
    config: FormConfig,
    initializer: FormInitializer<P, R>
): Form<R, P, F, I> = Form(heading, details, this, config, initializer)

fun <R, P, F : Fields<P>> F.toForm(
    heading: String,
    details: String,
    config: FormConfig,
    initializer: FormInitializer<P, R>
): Form<R, P, F, Input<F>> = toInput().toForm(heading, details, config, initializer)
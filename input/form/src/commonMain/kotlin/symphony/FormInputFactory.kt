package symphony

fun <O, P, F : Fields<P>, I : Input<F>> I.toForm(
    heading: String,
    details: String,
    config: FormConfig,
    initializer: FormInitializer<P, O>
): Form<O, P, F, I> = Form(heading, details, this, config, initializer)
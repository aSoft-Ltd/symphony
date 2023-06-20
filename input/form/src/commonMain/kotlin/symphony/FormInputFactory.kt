package symphony

fun <O, P, F : Fields<P>, I : Input<F>> I.toForm(
    heading: String,
    details: String,
    config: FormConfig,
    initializer: FormInitializer<P, O>
): FormInput<O, P, F, I> = FormInput(heading, details, this, config, initializer)
package symphony

fun <P, O : Any, F : Fields<O>> F.toInput(
    hidden: Boolean = false,
    config: SubmitConfig = SubmitConfig(),
    builder: SubmitBuilder<O, P> = { onSuccess { config.logger.log("Success") } }
): Input<F> = Input(
    fields = this,
    hidden = hidden,
    config = config,
    builder = builder as SubmitBuilder<*, *>
)
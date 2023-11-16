@file:OptIn(ExperimentalTypeInference::class)

package symphony

import symphony.internal.FormImpl
import kotlin.experimental.ExperimentalTypeInference
import lexi.LoggerFactory
import symphony.internal.FormImpl2
import symphony.internal.FormOptions

fun <R, P : Any, F : Fields<P>> Form(
    heading: String,
    details: String,
    fields: F,
    config: SubmitConfig,
    visibility: Visibility,
    builder: SubmitBuilder<P, R>
): Form<R, P, F> = FormImpl(
    heading = heading,
    details = details,
    fields = fields,
    config = config,
    visibility = visibility,
    builder = builder,
)

fun <R, P : Any, F : Fields<P>> F.toForm(
    heading: String,
    details: String,
    logger: LoggerFactory,
    exitOnSuccess: Boolean = true,
    visibility: Visibility = Visibilities.Visible,
    builder: SubmitBuilder<P, R>
): Form<R, P, F> = FormImpl2(
    FormOptions(
        heading = heading,
        details = details,
        fields = this,
        visibility = visibility,
        exitOnSuccess = exitOnSuccess,
        logger = logger,
        actions = SubmitActionsBuilder<P, R>().apply { builder() }
    )
)

fun <R, P : Any, F : Fields<P>> Form(
    heading: String,
    details: String,
    fields: F,
    config: SubmitConfig,
    builder: SubmitBuilder<P, R>
): Form<R, P, F> = FormImpl(
    heading = heading,
    details = details,
    fields = fields,
    config = config,
    visibility = Visibilities.Visible,
    builder = builder,
)

fun <R, P : Any, F : Fields<P>> F.toForm(
    heading: String,
    details: String,
    config: SubmitConfig,
    visibility: Visibility,
    builder: SubmitBuilder<P, R>
): Form<R, P, F> = FormImpl(
    heading = heading,
    details = details,
    fields = this,
    config = config,
    visibility = visibility,
    builder = builder,
)

fun <R, P : Any, F : Fields<P>> F.toForm(
    heading: String,
    details: String,
    config: SubmitConfig,
    @BuilderInference builder: SubmitBuilder<P, R>
): Form<R, P, F> = FormImpl(
    heading = heading,
    details = details,
    fields = this,
    config = config,
    visibility = Visibilities.Visible,
    builder = builder,
)
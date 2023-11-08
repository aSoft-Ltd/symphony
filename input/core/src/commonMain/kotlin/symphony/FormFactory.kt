@file:OptIn(ExperimentalTypeInference::class)

package symphony

import symphony.internal.FormImpl
import kotlin.experimental.ExperimentalTypeInference

fun <R, P : Any, F : Fields<P>> Form(
    heading: String,
    details: String,
    fields: F,
    config: SubmitConfig,
    visibility: Visibility,
    builder: SubmitBuilder<P, R>
) : Form<R,P,F> = FormImpl(
    heading = heading,
    details = details,
    fields = fields,
    config = config,
    visibility = visibility,
    builder = builder,
)

fun <R, P : Any, F : Fields<P>> Form(
    heading: String,
    details: String,
    fields: F,
    config: SubmitConfig,
    builder: SubmitBuilder<P, R>
) : Form<R,P,F> = FormImpl(
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
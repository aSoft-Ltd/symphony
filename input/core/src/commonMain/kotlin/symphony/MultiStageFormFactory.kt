package symphony

import kollections.toList
import kollections.Collection
import symphony.internal.MultiStageFormImpl

fun <R : Any, O : Any, S : FormStage> Collection<S>.toForm(
    output: O,
    config: SubmitConfig,
    visibility: Visibility,
    builder: SubmitBuilder<O, R>
): MultiStageForm<R, O, S> = MultiStageFormImpl(
    output = output,
    stages = toList(),
    visibility = visibility,
    config = config,
    builder = builder
)
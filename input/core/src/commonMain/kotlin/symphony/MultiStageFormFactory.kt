package symphony

import kollections.toIList
import symphony.internal.MultiStageFormImpl

fun <R : Any, O : Any, S : FormStage> Collection<S>.toForm(
    output: O,
    config: SubmitConfig,
    visibility: Visibility,
    builder: SubmitBuilder<O, R>
): MultiStageForm<R, O, S> = MultiStageFormImpl(
    output = output,
    stages = toIList(),
    visibility = visibility,
    config = config,
    builder = builder
)
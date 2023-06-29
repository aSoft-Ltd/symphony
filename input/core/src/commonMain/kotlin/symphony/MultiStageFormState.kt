@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport
import kotlin.js.JsName

data class MultiStageFormState<out R, out O, out S : FormStage>(
    val visibility: Visibility,
    val stages: List<S>,
    val stage: StageState<S>,
    val phase: FormPhase<O, R>,
    val output: O
) {
    data class StageState<out S>(
        val current: S,
        val isFirst: Boolean,
        val isLast: Boolean,
    )

    data class Progress(
        val step: Int,
        val total: Int
    ) {
        val percentage get() = (step * 100) / total
    }

    val progress = Progress(stages.indexOf(stage.current) + 1, stages.size)

    companion object {
        @JsName("initial")
        operator fun <R, O, S : FormStage> invoke(
            visibility: Visibility,
            stages: List<S>,
            output: O,
            phase: FormPhase<O, R>,
        ) = MultiStageFormState(
            visibility = visibility,
            stages = stages,
            stage = stages.first(),
            output = output,
            phase = phase
        )

        @JsName("of")
        operator fun <R, O, S : FormStage> invoke(
            visibility: Visibility,
            stages: List<S>,
            stage: S,
            output: O,
            phase: FormPhase<O, R>
        ) = MultiStageFormState(
            visibility = visibility,
            stages = stages,
            stage = StageState(
                current = stage,
                isFirst = stages.first() == stage,
                isLast = stages.last() == stage
            ),
            output = output,
            phase = phase
        )
    }

    internal fun prev(): MultiStageFormState<R, O, S> {
        if (stage.isFirst) return this
        val curr = stage.current
        val s = stages[stages.indexOf(curr) - 1]
        return MultiStageFormState(visibility, stages, s, output, phase)
    }

    internal fun next(): MultiStageFormState<R, O, S> {
        if (stage.isLast) return this
        val curr = stage.current
        val s = stages[stages.indexOf(curr) + 1]
        return MultiStageFormState(visibility, stages, s, output, phase)
    }
}
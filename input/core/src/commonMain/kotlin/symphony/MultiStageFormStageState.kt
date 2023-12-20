@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kollections.first
import kollections.get
import kollections.indexOf
import kollections.last
import kollections.size
import kotlinx.JsExport
import kotlin.js.JsName

data class MultiStageFormStageState<out R, out O, out S : FormStage>(
    val visibility: Visibility,
    val stages: List<S>,
    val stage: MultiFormStageState<S>,
    val phase: FormPhase<O, R>,
    val output: O
) {
    val progress = MultiStageFormStageProgress(stages.indexOf(stage.current) + 1, stages.size)

    companion object {
        @JsName("initial")
        operator fun <R, O, S : FormStage> invoke(
            visibility: Visibility,
            stages: List<S>,
            output: O,
            phase: FormPhase<O, R>,
        ) = MultiStageFormStageState(
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
        ) = MultiStageFormStageState(
            visibility = visibility,
            stages = stages,
            stage = MultiFormStageState(
                current = stage,
                isFirst = stages.first() == stage,
                isLast = stages.last() == stage
            ),
            output = output,
            phase = phase
        )
    }

    internal fun prev(): MultiStageFormStageState<R, O, S> {
        if (stage.isFirst) return this
        val curr = stage.current
        val s = stages[stages.indexOf(curr) - 1]
        return MultiStageFormStageState(visibility, stages, s, output, phase)
    }

    internal fun next(): MultiStageFormStageState<R, O, S> {
        if (stage.isLast) return this
        val curr = stage.current
        val s = stages[stages.indexOf(curr) + 1]
        return MultiStageFormStageState(visibility, stages, s, output, phase)
    }
}
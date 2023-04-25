@file:Suppress("NOTHING_TO_INLINE")

package presenters.collections

import kotlin.jvm.JvmSynthetic
import presenters.collections.internal.ColumnsManagerImpl

/*
 * DEAR DEVELOPER,
 * Do not mark this class as inline, because it tends to increase bundle size
 * due to very long columns declarations that can be found in complex tables
 */
@Deprecated("use symphony instead")
@JvmSynthetic
fun <D> columnsOf(
    columns: Collection<Column<D>> = setOf(),
    block: ColumnsBuilder<D>.() -> Unit
): ColumnsManager<D> = ColumnsManagerImpl(ColumnsBuilder(columns).apply(block))

@Deprecated("use symphony instead")
@JvmSynthetic
inline fun <D> columnsOf(): ColumnsManager<D> = ColumnsManagerImpl(ColumnsBuilder())
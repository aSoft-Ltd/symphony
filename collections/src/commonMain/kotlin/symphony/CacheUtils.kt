@file:Suppress("NOTHING_TO_INLINE")

package symphony

import keep.Cache

inline fun Cache.removeSelectedItem() = remove(CacheKeys.SELECTED_ITEM)
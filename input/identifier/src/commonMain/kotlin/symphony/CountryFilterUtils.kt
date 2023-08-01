@file:Suppress("NOTHING_TO_INLINE")

package symphony

import geo.Country

inline fun Country.matches(key: String) = label.startsWith(key) || code.startsWith(key) || dialingCode.startsWith(key.replace("+", ""))
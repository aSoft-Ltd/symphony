package symphony

internal object PaginationConstants {
    object Kind {
        const val Key = "kind"

        object Value {
            const val Cursor = "cursor"
            const val Offset = "offset"
        }
    }

    object Direction {
        const val KEY = "direction"

        object Value {
            const val Forward = "forward"
            const val Backward = "backward"
        }
    }

    const val Capacity = "capacity"
    const val Reference = "reference"
}
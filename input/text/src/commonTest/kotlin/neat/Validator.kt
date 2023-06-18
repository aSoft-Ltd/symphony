package neat

interface Validator<in T> {
    val label: String
    fun validate(value: T): Validity<@UnsafeVariance T>
}
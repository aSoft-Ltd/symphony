package symphony

fun interface Submitter<out T> {
    fun onSubmit(output: @UnsafeVariance T)
}
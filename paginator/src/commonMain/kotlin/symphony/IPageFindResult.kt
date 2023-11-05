package symphony

interface IPageResult<out T> {
    val row: Row<T>
}
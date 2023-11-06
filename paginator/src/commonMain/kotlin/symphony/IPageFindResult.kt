package symphony

interface IPageFindResult<out T> {
    val page: IPage
    val row: Row<T>
}
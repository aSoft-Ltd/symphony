package symphony

interface PageFindResult<out T> {
    val page: Page
    val row: Row<T>
}
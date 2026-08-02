import kommander.expect
import kommander.toBe
import symphony.BackwardCursor
import symphony.CursorPaginationParams
import symphony.ForwardCursor
import symphony.OffsetPaginationParams
import symphony.PaginationConstants
import symphony.PaginationParams
import kotlin.test.Test

class QueryParamsTest {
    @Test
    fun should_convert_a_forward_cursor_based_params_to_query() {
        val params = CursorPaginationParams(
            cursor = ForwardCursor("1234"),
            capacity = 10
        )
        val map = params.toQueryMap()
        expect(map[PaginationConstants.Kind.Key]).toBe(PaginationConstants.Kind.Value.Cursor)
        expect(map[PaginationConstants.Direction.KEY]).toBe(PaginationConstants.Direction.Value.Forward)
        expect(map[PaginationConstants.Capacity]).toBe("10")
        expect(map[PaginationConstants.Reference]).toBe("1234")
    }

    @Test
    fun should_be_able_to_be_construct_a_forward_param_from_query_params() {
        val query = "https://asoft.co.tz/employees?kind=cursor&reference=1234&direction=forward&capacity=10"
        val params = expect(PaginationParams.from(query)).toBe<CursorPaginationParams>()
        expect(params.cursor).toBe<ForwardCursor>()
        expect(params.cursor?.value).toBe("1234")
        expect(params.capacity).toBe(10)
    }

    @Test
    fun should_convert_a_backward_cursor_based_params_to_query() {
        val params = CursorPaginationParams(
            cursor = BackwardCursor("1234"),
            capacity = 10
        )
        val map = params.toQueryMap()
        expect(map[PaginationConstants.Kind.Key]).toBe(PaginationConstants.Kind.Value.Cursor)
        expect(map[PaginationConstants.Direction.KEY]).toBe(PaginationConstants.Direction.Value.Backward)
        expect(map[PaginationConstants.Capacity]).toBe("10")
        expect(map[PaginationConstants.Reference]).toBe("1234")
    }

    @Test
    fun should_be_able_to_be_construct_a_backward_param_from_query_params() {
        val query = "https://asoft.co.tz/employees?kind=cursor&reference=1234&direction=backward&capacity=10"
        val params = expect(PaginationParams.from(query)).toBe<CursorPaginationParams>()
        expect(params.cursor).toBe<BackwardCursor>()
        expect(params.cursor?.value).toBe("1234")
        expect(params.capacity).toBe(10)
    }

    @Test
    fun should_be_able_to_be_construct_an_offset_param_from_query_params() {
        val query = "https://asoft.co.tz/employees?kind=offset&reference=1234&capacity=10"
        val params = expect(PaginationParams.from(query)).toBe<OffsetPaginationParams>()
        expect(params.offset).toBe(1234)
        expect(params.capacity).toBe(10)
    }

    @Test
    fun should_convert_on_offset_based_params_to_query() {
        val params = OffsetPaginationParams(
            offset = 2,
            capacity = 12
        )
        val map = params.toQueryMap()
        expect(map[PaginationConstants.Kind.Key]).toBe(PaginationConstants.Kind.Value.Offset)
        expect(map[PaginationConstants.Direction.KEY]).toBe(null)
        expect(map[PaginationConstants.Capacity]).toBe("12")
        expect(map[PaginationConstants.Reference]).toBe("2")
    }

}
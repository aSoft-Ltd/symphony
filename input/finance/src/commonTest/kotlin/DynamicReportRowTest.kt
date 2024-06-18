import kommander.expect
import symphony.DynamicReportRowImpl
import kotlin.test.Test

class DynamicReportRowTest {

    private val row = DynamicReportRowImpl("Row")

    @Test
    fun should_be_able_to_create_an_empty_sheet() {
        expect(row.total.output).toBe(0.0)
        expect(row.container.output).toBe(false)
    }

    @Test
    fun should_be_able_to_expand_a_container() {
        expect(row.total.output).toBe(0.0)
        expect(row.container.output).toBe(false)
        row.expand()
        expect(row.total.output).toBe(0.0)
        expect(row.container.output).toBe(true)
    }

    @Test
    fun should_be_able_to_colapse_a_container() {
        expect(row.total.output).toBe(0.0)
        expect(row.container.output).toBe(false)
        row.expand()
        expect(row.total.output).toBe(0.0)
        expect(row.container.output).toBe(true)
        row.collapse()
        expect(row.total.output).toBe(0.0)
        expect(row.container.output).toBe(false)
    }

    @Test
    fun should_be_able_to_add_a_child() {
        expect(row.total.output).toBe(0.0)
        expect(row.container.output).toBe(false)
        row.expand()
        val r = row.add()
        expect(r?.total?.output).toBe(0.0)
    }

    @Test
    fun should_bubble_the_total_up() {
        expect(row.total.output).toBe(0.0)
        expect(row.container.output).toBe(false)
        row.expand()
        val r = row.add()
        r?.total?.set(value = 1000.0)
        expect(r?.total?.output).toBe(1000.0)
        expect(row.total.output).toBe(1000.0)
    }

    @Test
    fun should_be_able_to_change_the_total_when_removed() {
        expect(row.total.output).toBe(0.0)
        expect(row.container.output).toBe(false)
        row.expand()
        val r1 = row.add()
        r1?.total?.set(value = 1000.0)
        expect(r1?.total?.output).toBe(1000.0)
        expect(row.total.output).toBe(1000.0)

        val r2 = row.add()
        r2?.total?.set(value = 500.0)
        expect(r2?.total?.output).toBe(500.0)
        expect(row.total.output).toBe(1500.0)

        row.remove(r1)
        expect(row.total.output).toBe(500.0)
    }
}
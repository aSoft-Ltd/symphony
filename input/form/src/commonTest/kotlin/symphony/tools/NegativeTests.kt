package symphony.tools

import kommander.Expect
import kommander.expect

fun PersonFields.setInvalidValues() {
    name.clear()
    nickname.set("wizo")
    scholar.set(null)
}

fun Expect<PersonFields>.toBeInValidAndHaveInValidValues() {
    val output = value.output
    expect(output.name).toBe("")
    expect(output.nickname).toBe("wizo")
    expect(output.scholar).toBe(null)

    val errors = listOf(
        "name is required, but was null",
    )
    expect(value.state.value.feedbacks.errors).toBe(errors)
}
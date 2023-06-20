package symphony.tools

import kommander.Expect
import kommander.expect

fun PersonFields.setInvalidValues() {
    fName.set("a")
    lName.clear()
    parent.clear()
    scholar.set(null)
    hobbies.clear()
}

fun Expect<PersonFields>.toBeInValidAndHaveInValidValues() {
    val output = value.output
    expect(output.fName).toBe("a")
    expect(output.scholar).toBe(null)

    val errors = listOf(
        "fName should have more than 2 character(s), but has 1 character(s) instead",
        "lName is required, but was null",
        "age is required, but was null",
        "hobbies collection should have more than 2 item(s), but has 0 items(s) instead"
    )
    expect(value.state.value.feedbacks.errors).toBe(errors)
}
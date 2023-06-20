package symphony.tools

import kommander.Expect
import kommander.expect

fun PersonFields.setInvalidValues() {
    name.clear()
    nickname.set("wizo")
}

fun Expect<PersonFields>.toBeInValidAndHaveInValidValues() {
    expect(value.name.output).toBe("")
    expect(value.nickname.output).toBe("wizo")
}
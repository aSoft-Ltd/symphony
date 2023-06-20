package symphony.tools

import kommander.Expect
import kommander.expect

fun PersonFields.setValidValues() {
    name.set("John")
    nickname.set("jeezzy")
}

fun Expect<PersonFields>.toBeValidAndHaveValidValues() {
    expect(value.output.name).toBe("John")
    expect(value.output.nickname).toBe("jeezzy")
}
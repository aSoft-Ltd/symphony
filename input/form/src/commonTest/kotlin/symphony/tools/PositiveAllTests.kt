package symphony.tools

import kommander.Expect
import kommander.expect

fun PersonFields.setAllValidValues() {
    name.set("John")
    nickname.set("jeezzy")
    parent.set(true)
    scholar.set(false)
    maritalStatus.select(MaritalStatus.Dating)
    futureMaritalStatus.select(null)
}

fun Expect<PersonFields>.toBeValidWithAllValuesSet() {
    val output = value.output
    expect(output.name).toBe("John")
    expect(output.nickname).toBe("jeezzy")
    expect(output.parent).toBe(true)
    expect(output.scholar).toBe(false)
    expect(output.maritalStatus).toBe(MaritalStatus.Dating)
    expect(output.futureMaritalStatus).toBe(null)
}
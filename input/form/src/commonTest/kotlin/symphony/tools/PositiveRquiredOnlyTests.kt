package symphony.tools

import kommander.Expect
import kommander.expect

fun PersonFields.setRequiredValidValues() {
    name.set("John")
    parent.set(true)
    maritalStatus.select(MaritalStatus.Dating)
}

fun Expect<PersonFields>.toBeValidWithRequiredValuesOnly() {
    val output = value.output
    expect(output.name).toBe("John")
    expect(output.nickname).toBe(null)
    expect(output.parent).toBe(true)
    expect(output.scholar).toBe(null)
    expect(output.maritalStatus).toBe(MaritalStatus.Dating)
    expect(output.futureMaritalStatus).toBe(null)
}
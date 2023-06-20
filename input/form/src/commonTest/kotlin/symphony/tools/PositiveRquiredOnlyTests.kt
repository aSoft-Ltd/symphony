package symphony.tools

import kommander.Expect
import kommander.expect

fun PersonFields.setRequiredValidValues() {
    fName.set("John")
    lName.set("Doe")
    nickname.clear()
    parent.set(true)
    scholar.clear()
    maritalStatus.select(MaritalStatus.Dating)
    hobbies.addSelectedItem(Hobby.Tech)
    hobbies.addSelectedItem(Hobby.Math)
    colors.clear()
    age.set(21)
}

fun Expect<PersonFields>.toBeValidWithRequiredValuesOnly() {
    val output = value.output
    expect(output.fName).toBe("John")
    expect(output.lName).toBe("Doe")
    expect(output.nickname).toBe(null)
    expect(output.parent).toBe(true)
    expect(output.scholar).toBe(null)
    expect(output.maritalStatus).toBe(MaritalStatus.Dating)
    expect(output.futureMaritalStatus).toBe(null)
    expect(output.hobbies).toBe(listOf(Hobby.Tech, Hobby.Math))
    expect(output.colors).toBeEmpty()
}
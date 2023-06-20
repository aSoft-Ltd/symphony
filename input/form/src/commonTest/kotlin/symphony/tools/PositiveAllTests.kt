package symphony.tools

import kommander.Expect
import kommander.expect

fun PersonFields.setAllValidValues() {
    fName.set("John")
    lName.set("Doe")
    nickname.set("jeezzy")
    parent.set(true)
    scholar.set(false)
    maritalStatus.select(MaritalStatus.Dating)
    futureMaritalStatus.select(null)
    hobbies.addSelectedItem(Hobby.Tech)
    hobbies.addSelectedItem(Hobby.Math)
    colors.addSelectedItem(Color.Red)
    age.set("21")
    weight.set(33.0)
}

fun Expect<PersonFields>.toBeValidWithAllValuesSet() {
    val output = value.output
    expect(output.fName).toBe("John")
    expect(output.lName).toBe("Doe")
    expect(output.nickname).toBe("jeezzy")
    expect(output.parent).toBe(true)
    expect(output.scholar).toBe(false)
    expect(output.maritalStatus).toBe(MaritalStatus.Dating)
    expect(output.futureMaritalStatus).toBe(null)
    expect(output.hobbies).toBe(listOf(Hobby.Tech, Hobby.Math))
    expect(output.colors).toBe(listOf(Color.Red))
    expect(output.age).toBe(21)
    expect(output.wightInKg).toBe(33.0)
}
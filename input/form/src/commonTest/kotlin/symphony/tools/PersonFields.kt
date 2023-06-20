package symphony.tools

import neat.min
import neat.required
import symphony.Fields
import symphony.boolean
import symphony.selectSingle
import symphony.name
import symphony.selectMany
import symphony.text
import symphony.toOption

class PersonFields : Fields<PersonOutput>(PersonOutput()) {
    val fName = name(output::fName) { required() }
    val lName = name(output::lName) { required() }
    val nickname = text(output::nickname) { optional() }
    val parent = boolean(output::parent)
    val scholar = boolean(output::scholar) { optional() }
    val maritalStatus = selectSingle(
        name = output::maritalStatus,
        items = MaritalStatus.values().toList(),
        mapper = { it.toOption() }
    )
    val futureMaritalStatus = selectSingle(
        name = output::futureMaritalStatus,
        items = MaritalStatus.values().toList(),
        mapper = { it.toOption() }
    )

    val hobbies = selectMany(
        name = output::hobbies,
        items = Hobby.values().toList(),
        mapper = { it.toOption() }
    ) {
        min(2)
        required()
    }

    val colors = selectMany(
        name = output::colors,
        items = Color.values().toList(),
        mapper = { it.toOption() }
    )
}
package symphony.tools

import neat.required
import symphony.Fields
import symphony.Option
import symphony.boolean
import symphony.selectSingle
import symphony.name
import symphony.text
import symphony.toOption

class PersonFields : Fields<PersonOutput>(PersonOutput()) {
    val name = name(output::name) { required() }
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
}
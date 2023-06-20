package symphony.tools

import neat.notBlank
import symphony.Fields
import symphony.name
import symphony.text

class PersonFields : Fields<PersonOutput>(PersonOutput()) {
    val name = name(output::name) {
        notBlank().required()
    }
    val nickname = text(output::nickname) { optional() }
}
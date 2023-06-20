package symphony.tools

import cinematic.Live
import symphony.Field
import symphony.Fields
import symphony.Input
import kotlin.reflect.KMutableProperty0

class PersonInput : Input<PersonFields>(PersonFields()), Field<PersonOutput> {
    override val state: Live<PersonOutput> get() = fields.state
}

fun <T : PersonOutput?> Fields<*>.person(
    name: KMutableProperty0<T>
): PersonInput = getOrCreate(name) {
    PersonInput()
}
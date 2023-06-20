package symphony.tools

import symphony.Fields
import symphony.Input
import kotlin.reflect.KMutableProperty0

class PersonInput : Input<PersonFields>(PersonFields())

fun <T : PersonOutput?> Fields<*>.person(
    name: KMutableProperty0<T>
): PersonInput = getOrCreate(name) {
    PersonInput()
}
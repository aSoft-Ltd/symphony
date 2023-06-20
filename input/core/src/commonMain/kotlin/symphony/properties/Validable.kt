package symphony.properties

import neat.Validity

interface Validable {
    fun validate(): Validity<*>
    fun validateToErrors(): Validity<*>
}

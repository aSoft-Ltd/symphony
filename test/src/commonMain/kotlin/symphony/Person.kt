@file:OptIn(ExperimentalUuidApi::class)

package symphony

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Person(val uid: String, val name: String = "Andy", val age: Int = 12) {
    companion object {
        val List by lazy { generate(9) }

        fun generate(count: Int) = buildList {
            repeat(count) {
                add(Person(uid = Uuid.generateV7().toHexString(), "Anderson ${it + 1}", age = 15 + it))
            }
        }
    }
}


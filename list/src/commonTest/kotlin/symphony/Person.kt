package symphony

import kollections.add
import kollections.buildList

data class Person(val name: String = "Andy", val age: Int = 12, val gender: Gender, val hobby: Hobby) {
    enum class Gender { Male, Female }

    enum class Hobby { Swimming, Cooking, Math }
    companion object {
        val List = buildList {
            repeat(30) {
                add(Person("Anderson $it", age = (10..20).random(), Gender.entries.random(), Hobby.entries.random()))
            }
        }
    }
}
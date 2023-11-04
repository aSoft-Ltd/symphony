package symphony

data class Person(val name: String = "Andy", val age: Int = 12, val gender: Gender) {
    enum class Gender { Male, Female }
    companion object {
        val List = List(30) { Person("Anderson $it", age = 10 + it, Gender.entries.random()) }
    }
}
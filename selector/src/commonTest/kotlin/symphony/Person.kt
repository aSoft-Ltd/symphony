package symphony

data class Person(val name: String = "Andy", val age: Int = 12) {
    companion object {
        val List = buildList {
            repeat(25) {
                add(Person("Anderson $it", age = 15 + it))
            }
        }
    }
}
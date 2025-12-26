package symphony


data class Person(val name: String = "Andy", val age: Int = 12) {
    companion object {
        val List = buildList {
            repeat(25){
                add(Person("Anderson $it", age = 15 + it))
            }
        }

        fun columns() = columnsOf<Person> {
            selectable()
            column("No") { it.number.toString() }
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
    }
}
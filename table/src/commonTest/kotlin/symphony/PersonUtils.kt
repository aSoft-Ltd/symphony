package symphony


fun Person.Companion.columns() = columnsOf<Person> {
    selectable()
    column("No") { it.number.toString() }
    column("name") { it.item.name }
    column("age") { it.item.age.toString() }
}
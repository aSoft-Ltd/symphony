# Symphony

Symphony is your abstract kotlin multiplatform headless ui. It lets write code common code, 
dictate screen behaviour and let the only worry about the UI

## Setup
```kotlin
dependencies {
    implementation("tz.co.asoft:symphony-table:<version>")
    implementation("tz.co.asoft:symphony-list:<version>")
    implementation("tz.co.asoft:symphony-input-core:<version>")
    implementation("tz.co.asoft:symphony-input-text:<version>")
    implementation("tz.co.asoft:symphony-input-choice:<version>")
}
```

## Usage

Symphony is a general purpose headless ui. it supports input fields, forms, tables, lists, together with pagination,
selection, actions and columns management and many more.

Consider the following example class
```kotlin
data class Person(var name: String?, var age: String?)
```

### Forms
Capturing the contents of the person's attribute from a form can be achieved as follows

```kotlin
class TestFormFields(default: Person) : Fields<Person>(default) {
    val name = text(
        name = output::name,
        label = "Enter name",   // optional
        hint = "e.g. John Doe", // optional field
        onChange = {            // optional
            // doSomething
        }
    )
    val age = text(
        name = output::age,
        label = "Age",          // optional
        hint = "e.g. User Age"  // optional
    )
}

val form = TestFormFields(Person("John",null)).toForm(
    heading = "Person form",
    details = "Enter person's details"
) {
    onCancel {
        println("Canceled")
    }
    
    onSubmit { output->
        println("Submitted name: ${output.name}")
        println("Submitted age: ${output.age}")
    }
}

val res = form.submit().await()
```

This model makes it easier to write, 
create and test forms right in common code, and create a user interface that listens
to this forms field's and react accordingly.

It has validations backed in so that you get the data that you need

### Table
Symphony comes with a headless table as well. Rendering a list of people can be achieved as follow
```kotlin
val data = listOf(
    Person("John",21),
    Person("Jane",22),
    Person("Jake",18),
    Person("Jill",23),
)

val table = tableOf(data) {
    selectable() // to add the select column
    column("No") { it.item.number }
    column("name") { it.item.name }
    column("age") {it.name.age }
}
```

this table can be used to render it's information on any UI, including the console
```kotlin
[ ]    No   name    age   

[ ]    1    John    21    
[ ]    2    Jane    22    
[ ]    3    Jake    18    
[ ]    4    Jill    23 
```

This project is on going and has not been fully documented yet. But it is being used internally by our team
with a lot of success
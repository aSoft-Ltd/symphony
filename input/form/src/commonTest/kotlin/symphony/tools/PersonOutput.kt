package symphony.tools

import kollections.iEmptyList
import kollections.List

data class PersonOutput(
    var fName: String = "",
    var lName: String = "",
    var age: Int? = null,
    var wightInKg: Double = 0.0,
    var nickname: String? = null,
    var parent: Boolean = false,
    var scholar: Boolean? = null,
    var maritalStatus: MaritalStatus = MaritalStatus.Single,
    var futureMaritalStatus: MaritalStatus? = null,
    var hobbies: List<Hobby> = iEmptyList(),
    var colors: List<Color> = iEmptyList()
)

enum class MaritalStatus {
    Single, Dating, Married, Divorced, Widowed
}

enum class Hobby {
    Tech, Art, Math
}

enum class Color {
    Red, Green, Blue
}
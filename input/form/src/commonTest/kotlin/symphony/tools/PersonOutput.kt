package symphony.tools

data class PersonOutput(
    var name: String = "",
    var nickname: String? = null,
    var parent: Boolean = false,
    var scholar: Boolean? = null,
    var maritalStatus: MaritalStatus = MaritalStatus.Single,
    var futureMaritalStatus: MaritalStatus? = null
)

enum class MaritalStatus {
    Single, Dating, Married, Divorced, Widowed
}
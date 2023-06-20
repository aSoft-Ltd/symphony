package symphony.tools

import symphony.Fields

class CompanyFields : Fields<CompanyOutput>(CompanyOutput()) {
    val director = person(output::director)
}
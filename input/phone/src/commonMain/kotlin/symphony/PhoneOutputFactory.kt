package symphony

import nation.Country
import sim.Phone

fun Phone.toOutput() : PhoneOutput? {
    val country = Country.entries.firstOrNull { it.dialingCode==code } ?: return null
    val body = this.body.toLongOrNull() ?: return null
    return PhoneOutput(country,body)
}
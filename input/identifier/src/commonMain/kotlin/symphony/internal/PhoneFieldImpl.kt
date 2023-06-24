package symphony.internal

import cinematic.mutableLiveOf
import geo.Country
import kollections.List
import kollections.iEmptyList
import kollections.toIList
import neat.ValidationFactory
import neat.Validity
import neat.custom
import neat.required
import symphony.Feedbacks
import symphony.Label
import symphony.Option
import symphony.PhoneField
import symphony.PhoneFieldState
import symphony.PhoneOutput
import symphony.toErrors
import symphony.toWarnings
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class PhoneFieldImpl<O : PhoneOutput?>(
    val name: KMutableProperty0<O>,
    label: String,
    value: O,
    hidden: Boolean,
    hint: String,
    country: Country?,
    factory: ValidationFactory<O>?
) : PhoneField<O> {
    protected val validator = custom<O>(label).configure(factory)

    override fun validate() = validator.validate(output)

    override fun set(value: O) {
        val res = validator.validate(value)
        name.set(value)
        state.value = state.value.copy(
            country = res.value?.country,
            body = res.value?.body,
            feedbacks = Feedbacks(res.toWarnings())
        )
    }

    override fun setCountry(country: Country?) {
        val s = state.value.copy(country = country)
        val o = s.output as? O
        if (o != null) return set(o)
        state.value = s
    }

    override fun setBody(long: Long?) {
        val s = state.value.copy(body = long)
        val o = s.output as? O
        if (o != null) return set(o)
        state.value = s
    }

    override fun setBody(value: String?) = setBody(value?.toLongOrNull())

    override fun hide(hide: Boolean?) {
        state.value = state.value.copy(hidden = hide == true)
    }

    override fun show(show: Boolean?) {
        state.value = state.value.copy(hidden = show != true)
    }

    override fun validateToErrors(): Validity<O> {
        val res = validator.validate(output)
        state.value = state.value.copy(feedbacks = Feedbacks(res.toErrors()))
        return res
    }

    override fun finish() {
        state.stopAll()
    }

    override fun reset() {
        state.value = initial
    }

    override fun clear() {
        state.value = initial.copy(country = null, body = null)
    }

    val initial: PhoneFieldState = PhoneFieldState(
        name = name.name,
        label = Label(label, this.validator.required),
        hidden = hidden,
        hint = hint,
        required = this.validator.required,
        country = value?.country ?: country,
        body = value?.body,
        feedbacks = Feedbacks(iEmptyList()),
    )

    override val state by lazy { mutableLiveOf(initial) }

    override val output: O? get() = state.value.output as? O

    override val selectedCountry: Country? get() = state.value.country

    override val selectedOption: Option? get() = selectedCountry?.let(mapper)

    override val hidden: Boolean get() = state.value.hidden

    override val countries: List<Country> by lazy { Country.values().toIList() }

    private val mapper = { c: Country -> Option(c.label, c.code, c == selectedCountry) }

    override fun options(withSelect: Boolean): List<Option> = (if (withSelect) {
        listOf(Option("Select ${state.value.label.capitalizedWithoutAstrix()}", ""))
    } else {
        emptyList()
    } + countries.toList().map { mapper(it) }).toIList()

    override fun selectCountryOption(option: Option) {
        val country = countries.find { mapper(it).value == option.value }
        if (country != null) setCountry(country)
    }

    override fun selectCountryValue(optionValue: String) {
        val item = countries.find { mapper(it).value == optionValue }
        if (item != null) setCountry(item)
    }

    override fun selectCountryLabel(optionLabel: String) {
        val item = countries.find { mapper(it).label == optionLabel }
        if (item != null) setCountry(item)
    }

    override fun unsetCountry() {
        state.value = state.value.copy(country = null)
    }
}
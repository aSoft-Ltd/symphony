package presenters

@Deprecated("use symphony")
interface SerializableLiveFormattedData<I, O> : InputField, SerializableLiveData<O>, LiveDataFormatted<I, O>
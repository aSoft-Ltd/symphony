package presenters

@Deprecated("use symphony")
interface SerializableLiveData<D> : InputField, Serializable<D>, LiveData<D>
package symphony

interface SerializableLiveData<D> : InputField, Serializable<D>, LiveData<D>
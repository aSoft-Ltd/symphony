package presenters

import kollections.List

@Deprecated("use symphony")
interface SerializableLiveDataList<D> : InputField, SerializableLiveData<List<D>>, LiveDataList<D>
package symphony

import kollections.List

interface SerializableLiveDataList<D> : InputField, SerializableLiveData<List<D>>, LiveDataList<D>
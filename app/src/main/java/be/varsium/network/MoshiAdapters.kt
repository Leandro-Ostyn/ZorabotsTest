package be.varsium.network

import android.annotation.SuppressLint
import com.squareup.moshi.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class GenericCollectionAdapter<TItem : Any, TCollection : MutableCollection<TItem>>(
    clazz: Class<TItem>,
    moshi: Moshi,
    private val createEmptyCollection: () -> TCollection
) : JsonAdapter<TCollection>() {
    private val typeAdapter = moshi.adapter<TItem>(clazz)

    @FromJson
    override fun fromJson(reader: JsonReader): TCollection? {
        val result = createEmptyCollection()

        reader.beginArray()

        while (reader.hasNext()) {
            val item = typeAdapter.fromJson(reader)
            if (item != null)
                result.add(item)
        }

        reader.endArray()

        return result
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: TCollection?) {
        writer.beginArray()

        if (value != null) {
            for (item in value) {
                typeAdapter.toJson(writer, item)
            }
        }

        writer.endArray()
    }
}

class GenericCollectionAdapterFactory<TCollection : MutableCollection<*>>(
    private val collectionClazz: Class<TCollection>,
    private val createEmptyCollection: () -> MutableCollection<Any>
) : JsonAdapter.Factory {
    @SuppressLint("NewApi")
    @Suppress("UNCHECKED_CAST")
    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        val paramType = type as? ParameterizedType ?: return null
        if (paramType.rawType.typeName != collectionClazz.typeName) return null
        if (paramType.actualTypeArguments.size != 1) return null
        val argTypeName = paramType.actualTypeArguments[0].typeName
        val argType = Class.forName(argTypeName) as Class<Any>

        return GenericCollectionAdapter(argType, moshi, createEmptyCollection)
    }
}
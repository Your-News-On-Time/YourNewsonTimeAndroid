package app.yournewsontime.data.model

import android.util.Log
import com.google.gson.*
import java.lang.reflect.Type

class MultimediaAdapter : JsonDeserializer<List<Multimedia>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): List<Multimedia> {
        Log.d("MultimediaAdapter", "🟢 Entró en el adaptador personalizado")

        return when {
            json.isJsonArray -> {
                Log.d("MultimediaAdapter", "🟢 multimedia es ARRAY")
                json.asJsonArray.map {
                    context.deserialize(it, Multimedia::class.java)
                }
            }
            json.isJsonObject -> {
                Log.d("MultimediaAdapter", "🟡 multimedia es OBJETO")
                listOf(context.deserialize(json, Multimedia::class.java))
            }
            else -> {
                Log.e("MultimediaAdapter", "🔴 multimedia no es ni array ni objeto")
                emptyList()
            }
        }
    }
}

package app.yournewsontime.data.model

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class NewYorkTimesResponse(
    val response: ResponseData
)

data class ResponseData(
    val docs: List<Article>
)

data class Article(
    val headline: Headline,
    val snippet: String,
    val lead_paragraph: String,
    val pub_date: String,
    @SerializedName("multimedia")
    val rawMultimedia: JsonElement, // 👈 esto en lugar de List<Multimedia>
    val web_url: String,
    val _id: String
)
fun Article.getMultimediaList(gson: Gson): List<Multimedia> {
    return when {
        rawMultimedia.isJsonArray -> {
            gson.fromJson(rawMultimedia, object : TypeToken<List<Multimedia>>() {}.type)
        }
        rawMultimedia.isJsonObject -> {
            listOf(gson.fromJson(rawMultimedia, Multimedia::class.java))
        }
        else -> emptyList()
    }
}


data class Headline(
    @SerializedName("main") val main: String?
)

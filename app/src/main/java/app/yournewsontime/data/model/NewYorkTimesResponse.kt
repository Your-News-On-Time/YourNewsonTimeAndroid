package app.yournewsontime.data.model

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
    val multimedia: List<Multimedia>,
    val web_url: String,
    val section_name: String?
)

data class Headline(val main: String)

data class Multimedia(
    val url: String,
    val type: String,  // Podría ser "image" o "photo"
    val height: Int?,
    val width: Int?
)
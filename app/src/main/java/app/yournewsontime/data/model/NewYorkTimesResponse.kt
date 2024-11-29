package app.yournewsontime.data.model

data class NewYorkTimesResponse(
    val response: ResponseData
)

data class ResponseData(
    val docs: List<Article>
)

data class Article(
    val headline: Headline,
    val snippet: String
)

data class Headline(
    val main: String
)

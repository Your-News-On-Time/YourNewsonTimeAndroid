package app.yournewsontime.data.model

data class NYTimesResponse(
    val response: ResponseData // Corresponde a la clave `response` en el JSON
)

data class ResponseData(
    val docs: List<Article> // Lista de artículos dentro de `response.docs`
)

data class Article(
    val headline: Headline, // Objeto que contiene el título principal del artículo
    val web_url: String     // URL del artículo
)

data class Headline(
    val main: String // El título principal del artículo
)

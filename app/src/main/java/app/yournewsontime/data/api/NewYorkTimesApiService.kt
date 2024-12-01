package app.yournewsontime.data.repository.api

import app.yournewsontime.data.model.NewYorkTimesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewYorkTimesApiService {
    @GET("search/v2/articlesearch.json")
    fun searchArticles(
        @Query("q") category: String,
        @Query("api-key") apiKey: String,
        @Query("begin_date") beginDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("pub_date") pub_date: String? = null,
        @Query("multimedia") multimedia: String? = null,
        //Linea para comprar la fecha de publicación
        //@Query("fq") fq: String = "pub_date:[2024-11-29 TO 2024-11-29]"
    ): Call<NewYorkTimesResponse>
}
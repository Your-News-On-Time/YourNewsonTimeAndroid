package app.yournewsontime.data.repository.api

import app.yournewsontime.data.model.NYTimesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTimesApiService {
    @GET("search/v2/articlesearch.json")
    fun searchArticles(
        @Query("q") query: String,
        @Query("api-key") apiKey: String
    ): Call<NYTimesResponse>
}
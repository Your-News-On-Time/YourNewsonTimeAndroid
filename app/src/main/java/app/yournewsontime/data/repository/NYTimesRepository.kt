package app.yournewsontime.data.repository

import app.yournewsontime.data.repository.api.NYTimesApiService
import app.yournewsontime.data.model.Article
import app.yournewsontime.data.model.NYTimesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NYTimesRepository(private val apiService: NYTimesApiService) {
    fun searchArticles(query: String, apiKey: String, callback: (List<Article>?, String?) -> Unit) {
        apiService.searchArticles(query, apiKey).enqueue(object : Callback<NYTimesResponse> {
            override fun onResponse(
                call: Call<NYTimesResponse>,
                response: Response<NYTimesResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body()?.response?.docs, null)
                } else {
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<NYTimesResponse>, t: Throwable) {
                callback(null, t.localizedMessage)
            }
        })
    }
}
package app.yournewsontime.data.repository

import app.yournewsontime.data.model.Article
import app.yournewsontime.data.model.NewYorkTimesResponse
import app.yournewsontime.data.repository.api.NewYorkTimesApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewYorkTimesRepository(private val apiService: NewYorkTimesApiService) {
    fun searchArticles(query: String, apiKey: String, callback: (List<Article>?, String?) -> Unit) {
        apiService.searchArticles(query, apiKey).enqueue(object : Callback<NewYorkTimesResponse> {
            override fun onResponse(
                call: Call<NewYorkTimesResponse>,
                response: Response<NewYorkTimesResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body()?.response?.docs, null)
                } else {
                    callback(null, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<NewYorkTimesResponse>, t: Throwable) {
                callback(null, t.localizedMessage)
            }
        })
    }
}
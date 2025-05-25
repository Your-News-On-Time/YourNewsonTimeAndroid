package app.yournewsontime.data.repository

import app.yournewsontime.data.model.Article
import app.yournewsontime.data.model.NewYorkTimesResponse
import app.yournewsontime.data.model.getMultimediaList
import app.yournewsontime.data.repository.api.NewYorkTimesApiService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewYorkTimesRepository(private val apiService: NewYorkTimesApiService) {
    fun searchArticles(
        category: String,
        apiKey: String,
        beginDate: String,
        endDate: String,
        callback: (List<Article>?, String?) -> Unit
    ) {
        apiService.searchArticles(category, apiKey, beginDate, endDate)
            .enqueue(object : Callback<NewYorkTimesResponse> {
                override fun onResponse(
                    call: Call<NewYorkTimesResponse>,
                    response: Response<NewYorkTimesResponse>
                ) {
                    if (response.isSuccessful) {
                        val gson = Gson()
                        val articles = response.body()?.response?.docs ?: emptyList()

                        // 🔍 DEBUG multimedia
                        println("🧾 MULTIMEDIA DEBUG:")
                        articles.forEach {
                            val mediaList = it.getMultimediaList(gson)
                            println("📰 ${it._id} → multimedia = ${mediaList.size}")
                            mediaList.forEachIndexed { i, media ->
                                println("🔍 Multimedia[$i] → url = ${media.url}, type = ${media.type}")
                            }
                        }

                        callback(articles, null)
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
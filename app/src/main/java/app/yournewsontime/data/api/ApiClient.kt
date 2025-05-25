package app.yournewsontime.data.repository.api

import app.yournewsontime.data.model.Multimedia
import app.yournewsontime.data.model.MultimediaAdapter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val multimediaListType = object : TypeToken<List<Multimedia>>() {}.type

object ApiClient {
    private const val BASE_URL = "https://api.nytimes.com/svc/"

    private val gson = GsonBuilder()
        .registerTypeAdapter(
            multimediaListType,
            MultimediaAdapter()
        )
        .create()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}

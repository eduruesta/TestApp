package com.example.testapp.retrofit
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {

        private const val BASE_URL = "https://www.reddit.com/r/subreddit"

        private var retrofit: Retrofit? = null

        @JvmStatic
        fun getApiService(): RetrofitApiService? {
            if (retrofit == null) {
                val gSon = GsonBuilder()
                    .setLenient()
                    .create()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gSon))
                    .build()
            }
            return retrofit?.create(RetrofitApiService::class.java)
        }
    }
}


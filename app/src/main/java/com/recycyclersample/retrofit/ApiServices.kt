package com.recycyclersample.retrofit

import com.recycyclersample.models.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Dyzbee on 09/04/2017.
 */
interface ApiServices {
    @GET("search?=Michael+jackson")
    fun getMusicData(
        @Query("term") query: String?
    ): Call<Response?>?
}
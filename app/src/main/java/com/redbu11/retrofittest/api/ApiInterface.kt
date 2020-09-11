package com.redbu11.retrofittest.api

import com.google.gson.JsonElement
import com.redbu11.retrofittest.datamodels.Post
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface for making API requests
 */
interface ApiInterface {
    @GET("/posts")
    suspend fun getPosts(): Response<JsonElement>

    @GET("/posts")
    suspend fun getSortedPosts(@Query("userId") userId: Int): Response<JsonElement>

    @GET("/posts/{id}")
    suspend fun getPost(@Path(value = "id") postId: Int): Response<JsonElement>

    @POST("/posts")
    suspend fun uploadPost(@Body post: Post) : Response<JsonElement>

    @PUT("/posts/{id}")
    suspend fun updatePost(@Path(value = "id") postId: Int, @Body post: Post) : Response<JsonElement>

    @PATCH("/posts/{id}")
    suspend fun updatePostWithPATCH(@Path(value = "id") postId: Int, @Body post: Post) : Response<JsonElement>

    @DELETE("/posts/{id}")
    suspend fun deletePost(@Path(value = "id") postId: Int) : Response<JsonElement>
}
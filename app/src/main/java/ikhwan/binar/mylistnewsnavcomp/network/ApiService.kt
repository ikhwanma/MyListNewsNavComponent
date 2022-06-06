package ikhwan.binar.mylistnewsnavcomp.network

import ikhwan.binar.mylistnewsnavcomp.model.GetNewsResponseItem
import ikhwan.binar.mylistnewsnavcomp.model.user.PostUserResponse
import ikhwan.binar.mylistnewsnavcomp.model.user.GetUserResponseItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/news")
    fun getAllNews(): Call<List<GetNewsResponseItem>>

    @GET("/user")
    fun getAllUser(): Call<List<GetUserResponseItem>>

    @POST("/user")
    fun addUsers(
        @Body user : PostUserResponse
    ): Call<GetUserResponseItem>
}
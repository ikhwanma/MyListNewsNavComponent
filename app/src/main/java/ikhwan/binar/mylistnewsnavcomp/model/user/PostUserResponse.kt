package ikhwan.binar.mylistnewsnavcomp.model.user

import com.google.gson.annotations.SerializedName

data class PostUserResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("umur")
    val umur: Int,
    @SerializedName("username")
    val username: String
)
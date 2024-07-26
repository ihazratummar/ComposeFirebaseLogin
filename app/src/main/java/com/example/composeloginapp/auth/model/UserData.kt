package com.example.composeloginapp.auth.model

data class UserData(
    var userId : String? = "",
    var fullname : String? = "",
    var email : String? = "",
    var imageUrl : String? = "",
){
    fun toMap() = mapOf(
        "userId" to userId,
        "fullname" to fullname,
        "email" to email,
        "imageUrl" to imageUrl
    )
}

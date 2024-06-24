package com.example.hechoamano.data.authentication

import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @SerializedName("token")
    var token: String,

    @SerializedName("userName")
    var userName: String
)
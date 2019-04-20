package com.arieharyana.mobilecomputing.ngopidiary

class ProfileModel {

    data class Data(
            val username: String,
            val profile_picture: String,
            val full_name: String,
            val bio: String,
            val website: String
    )
}
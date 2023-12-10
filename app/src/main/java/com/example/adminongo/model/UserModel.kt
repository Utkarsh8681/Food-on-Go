package com.example.adminongo.model

import android.provider.ContactsContract.CommonDataKinds.Phone

data class UserModel(

    val name:String? = null,
    val nameOfRestaurent:String? = null,
    val email:String? = null,
    val password:String? = null,
    val phone: String? = null,
    val address : String? = null

    )

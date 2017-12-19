package com.android.bootstrap.usecase.data.model

import com.google.gson.annotations.SerializedName

data class OwnerDataModel(@SerializedName("login") val login: String,
                          @SerializedName("html_url") val url: String) {
}
package com.android.bootstrap.usecase.data.model

import com.google.gson.annotations.SerializedName

data class RepoDataModel(@SerializedName("name") val name: String,
                         @SerializedName("description") val description: String,
                         @SerializedName("html_url") val url: String,
                         @SerializedName("fork") val isFork: Boolean,
                         @SerializedName("owner") val owner: OwnerDataModel)
package com.android.bootstrap.usecase.domain.model

data class Repo(val name: String,
                val description: String,
                val login: String,
                val isFork: Boolean,
                val htmlUrl: String,
                val ownerHtmlUrl: String)
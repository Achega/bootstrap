package com.android.bootstrap.repositories.model

data class Repository(val name: String,
                      val description: String,
                      val login: String,
                      val isFork: Boolean,
                      val htmlUrl: String,
                      val ownerHtmlUrl: String)
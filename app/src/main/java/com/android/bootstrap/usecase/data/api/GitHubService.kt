package com.android.bootstrap.usecase.data.api

import com.android.bootstrap.usecase.data.model.RepoDataModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
  companion object {
    private val DEFAULT_PAGE = 1
    private val DEFAULT_PER_PAGE = 10
  }

  @GET("/users/xing/repos")
  fun getRepos(@Query("page") page: Int = DEFAULT_PAGE, @Query("per_page") perPage: Int = DEFAULT_PER_PAGE)
      : Single<List<RepoDataModel>>
}
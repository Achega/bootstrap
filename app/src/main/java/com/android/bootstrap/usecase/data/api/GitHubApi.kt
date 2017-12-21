package com.android.bootstrap.usecase.data.api

import com.android.bootstrap.usecase.data.api.model.GitHubRequest
import com.android.bootstrap.usecase.data.model.RepoDataModel
import io.reactivex.Single
import retrofit2.Retrofit

class GitHubApi(private val retrofit: Retrofit) {
  fun getGitHubRepositories(gitHubRequest: GitHubRequest): Single<List<RepoDataModel>> =
      retrofit.create(GitHubService::class.java).getRepos(gitHubRequest.page, gitHubRequest.perPage)

}
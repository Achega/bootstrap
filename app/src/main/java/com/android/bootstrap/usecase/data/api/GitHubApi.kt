package com.android.bootstrap.usecase.data.api

import com.android.bootstrap.usecase.data.api.model.GitHubRequest
import com.android.bootstrap.usecase.data.model.RepoDataModel
import io.reactivex.Observable
import retrofit2.Retrofit

class GitHubApi(private val retrofit: Retrofit) {
  fun getGitHubRepositories(gitHubRequest: GitHubRequest): Observable<List<RepoDataModel>> {
    return retrofit.create(GitHubService::class.java).getRepos(gitHubRequest.page, gitHubRequest.perPage)
  }
}
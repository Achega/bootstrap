package com.android.bootstrap.usecase.data

import com.android.bootstrap.usecase.data.api.GitHubApi
import com.android.bootstrap.usecase.data.api.model.GitHubRequest
import com.android.bootstrap.usecase.domain.model.Repo
import com.android.bootstrap.usecase.domain.model.mapper.RepoDataToDomainMapper
import io.reactivex.Observable

class RepoRepository(private val gitHubApi: GitHubApi, private val repoDataToDomainMapper: RepoDataToDomainMapper) {
  fun getRepos(gitHubRequest: GitHubRequest): Observable<List<Repo>> =
      gitHubApi.getGitHubRepositories(gitHubRequest).map(repoDataToDomainMapper)
}
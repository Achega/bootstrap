package com.android.bootstrap.usecase.domain

import com.android.bootstrap.usecase.data.RepoRepository
import com.android.bootstrap.usecase.data.api.model.GitHubRequest

class GetReposUseCase(private val repoRepository: RepoRepository) {
  fun getRepositories(page: Int, perPage: Int) = repoRepository.getRepos(GitHubRequest(page, perPage))
}
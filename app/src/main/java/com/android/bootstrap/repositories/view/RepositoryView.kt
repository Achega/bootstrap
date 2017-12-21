package com.android.bootstrap.repositories.view

import com.android.bootstrap.usecase.domain.model.Repo

interface RepositoryView {
  fun showRepositories(repositories: List<Repo>)
  fun addRepositoriesToList(repositories: List<Repo>)
  fun showLoading()
  fun hideLoading()
  fun stopSearching()
  fun showError()
}
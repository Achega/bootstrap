package com.android.bootstrap.repositories.view

import com.android.bootstrap.usecase.domain.model.Repo

interface RepositoryView {
  fun showRepositories(repositories: List<Repo>)
  fun showError()
}
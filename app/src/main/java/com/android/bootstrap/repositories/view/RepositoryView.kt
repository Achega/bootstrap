package com.android.bootstrap.repositories.view

import com.android.bootstrap.repositories.model.Repository

interface RepositoryView {
  fun showRepositories(repositories: List<Repository>)
  fun showError()
}
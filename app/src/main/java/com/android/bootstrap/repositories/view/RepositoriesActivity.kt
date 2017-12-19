package com.android.bootstrap.repositories.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.bootstrap.R
import com.android.bootstrap.repositories.model.Repository
import com.android.bootstrap.repositories.presenter.RepositoriesPresenter

class RepositoriesActivity : AppCompatActivity(), RepositoryView {
  private val presenter: RepositoriesPresenter by lazy { RepositoriesPresenter(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_repositories)
    presenter.onRepositoriesLoaded()
  }

  override fun showRepositories(repositories: List<Repository>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun showError() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
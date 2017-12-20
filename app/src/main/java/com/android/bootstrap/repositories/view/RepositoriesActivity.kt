package com.android.bootstrap.repositories.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.bootstrap.R
import com.android.bootstrap.usecase.domain.model.Repo
import com.android.bootstrap.repositories.presenter.RepositoriesPresenter
import com.android.bootstrap.repositories.view.adapter.RepositoryAdapter
import com.android.bootstrap.usecase.GetRepositoriesUseCaseServiceLocator
import kotlinx.android.synthetic.main.activity_repositories.repositoriesRecyclerView
import org.jetbrains.anko.toast

class RepositoriesActivity : AppCompatActivity(), RepositoryView {
  private val presenter: RepositoriesPresenter by lazy {
    RepositoriesPresenter(this, GetRepositoriesUseCaseServiceLocator.provideGetRepositoriesUseCase())
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_repositories)
    repositoriesRecyclerView.layoutManager = LinearLayoutManager(this)
    presenter.onRepositoriesLoaded(1, 20)
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  override fun showRepositories(repositories: List<Repo>) {
    repositoriesRecyclerView.adapter = RepositoryAdapter(repositories) { toast(it.name) }
    repositories.forEach { Log.i(it.name, it.description) }
  }

  override fun showError() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
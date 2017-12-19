package com.android.bootstrap.repositories.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.android.bootstrap.R
import com.android.bootstrap.usecase.domain.model.Repo
import com.android.bootstrap.repositories.presenter.RepositoriesPresenter
import com.android.bootstrap.usecase.GetRepositoriesUseCaseServiceLocator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepositoriesActivity : AppCompatActivity(), RepositoryView {
  private val presenter: RepositoriesPresenter by lazy { RepositoriesPresenter(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_repositories)
    presenter.onRepositoriesLoaded()
    loadResults()
  }

  fun loadResults() {
    val useCase = GetRepositoriesUseCaseServiceLocator.provideGetRepositoriesUseCase()

    useCase.getRepositories(1, 1)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { it.forEach { Log.i("hola", it.isFork.toString()) } }
  }

  override fun showRepositories(repos: List<Repo>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun showError() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
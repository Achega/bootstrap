package com.android.bootstrap.repositories.presenter

import com.android.bootstrap.repositories.view.RepositoryView
import com.android.bootstrap.usecase.domain.GetRepositoriesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RepositoriesPresenter(private val view: RepositoryView, private val getRepositoriesUseCase: GetRepositoriesUseCase) {
  private val subscriptions: CompositeDisposable by lazy { CompositeDisposable() }
  fun onRepositoriesLoaded(page: Int, perPage: Int) {
    val subscription = getRepositoriesUseCase.getRepositories(page, perPage)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { view.showRepositories(it) }
    subscriptions.add(subscription)
  }

  fun onDestroy() {
    subscriptions.clear()
  }
}
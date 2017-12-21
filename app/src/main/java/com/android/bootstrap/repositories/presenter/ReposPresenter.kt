package com.android.bootstrap.repositories.presenter

import com.android.bootstrap.repositories.view.ReposView
import com.android.bootstrap.usecase.domain.GetReposUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Scheduler

class ReposPresenter(private val view: ReposView, private val getReposUseCase: GetReposUseCase,
                     private val ioScheduler: Scheduler = Schedulers.io(),
                     private val mainThreadScheduler: Scheduler = AndroidSchedulers.mainThread()) {
  companion object {
    private val ITEMS_PER_PAGE = 10
    private val DEFAULT_PAGE = 1
  }

  private val subscriptions: CompositeDisposable by lazy { CompositeDisposable() }
  fun onRepositoriesLoaded() {
    getRepositories(DEFAULT_PAGE)
  }

  fun onNewPageLoaded(page: Int) {
    getRepositories(page)
  }

  private fun getRepositories(page: Int) {
    view.showLoading()
    val subscription = getReposUseCase.getRepositories(page, ITEMS_PER_PAGE)
        .subscribeOn(ioScheduler)
        .observeOn(mainThreadScheduler)
        .subscribe(
            {
              view.addRepositoriesToList(it)
              if (it.size < 10) view.stopSearching()
              view.hideLoading()
            },
            {
              view.showError()
              view.hideLoading()
            }
        )
    subscriptions.add(subscription)
  }

  fun onDestroy() {
    subscriptions.clear()
  }
}
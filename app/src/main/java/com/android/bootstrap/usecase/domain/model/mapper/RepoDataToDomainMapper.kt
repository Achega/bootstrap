package com.android.bootstrap.usecase.domain.model.mapper

import com.android.bootstrap.usecase.data.model.RepoDataModel
import com.android.bootstrap.usecase.domain.model.Repo
import io.reactivex.functions.Function

class RepoDataToDomainMapper : Function<List<RepoDataModel>, List<Repo>> {
  override fun apply(repoDataModelList: List<RepoDataModel>): List<Repo> {
    return repoDataModelList.map { mapRepo(it) }
  }

  private fun mapRepo(repoDataModel: RepoDataModel) =
      with(repoDataModel) {
        Repo(name, description, owner.login, isFork, url, owner.url)
      }
}
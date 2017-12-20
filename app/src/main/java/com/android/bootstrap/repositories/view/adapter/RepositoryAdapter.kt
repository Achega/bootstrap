package com.android.bootstrap.repositories.view.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.bootstrap.R
import com.android.bootstrap.usecase.domain.model.Repo

class RepositoryAdapter(private val repositories: List<Repo>, private val onItemClick: (Repo) -> Unit)
  : RecyclerView.Adapter<RepositoryViewHolder>() {
  override fun getItemCount() = repositories.size

  override fun onBindViewHolder(viewHolder: RepositoryViewHolder, position: Int) {
    viewHolder.bind(repositories[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_view, parent, false)
    return RepositoryViewHolder(view, onItemClick)
  }
}
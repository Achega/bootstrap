package com.android.bootstrap.repositories.view.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.bootstrap.R
import com.android.bootstrap.usecase.domain.model.Repo
import kotlinx.android.synthetic.main.repository_view.view.repository_view_description
import kotlinx.android.synthetic.main.repository_view.view.repository_view_login
import kotlinx.android.synthetic.main.repository_view.view.repository_view_name

class RepositoryViewHolder(itemView: View, private val onItemClick: (Repo) -> Unit = {})
  : RecyclerView.ViewHolder(itemView) {
  fun bind(repo: Repo) {
    with(repo) {
      itemView.repository_view_name.text = name
      itemView.repository_view_description.text = description
      itemView.repository_view_login.text = getLoginText(login)
      setBackgroundColor(isFork)
      itemView.setOnLongClickListener { onItemClick(repo); true }
    }
  }

  private fun setBackgroundColor(isFork: Boolean) {
    val colorId = getColorId(isFork)
    itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, colorId))
  }

  private fun getColorId(isFork: Boolean): Int = if (isFork) R.color.lightGreen else R.color.white

  private fun getLoginText(userName: String) = itemView.context.getString(R.string.user_name, userName)
}
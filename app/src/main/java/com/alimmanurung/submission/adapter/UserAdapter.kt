package com.alimmanurung.submission.adapter

import User
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.alimmanurung.submission.R
import kotlinx.android.synthetic.main.item_users.view.*
class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    var mUser = ArrayList<User>()
        set(mUser) {
            if (mUser.size > 0) this.mUser.clear()
            this.mUser.addAll(mUser)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(mUser[position])

    override fun getItemCount(): Int = mUser.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                tv_name.text = user.uname
                Glide.with(context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(60, 60))
                    .into(avatar)

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                }
            }
        }
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}
package com.alimmanurung.submission.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alimmanurung.submission.R
import com.alimmanurung.submission.adapter.UserAdapter
import com.alimmanurung.submission.viewmodel.FollowViewModel
import kotlinx.android.synthetic.main.fragment_follow.*

class FollowFragment : Fragment() {
    companion object {
        const val OBJECT = "object"
    }
    private lateinit var userAdapter: UserAdapter
    private lateinit var followViewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(OBJECT) }?.apply {
            val result: Array<String> = getStringArray(OBJECT)!!
            setupViewModel(result)
            showRecyclerListViewUser()
        }
    }

    private fun showRecyclerListViewUser() {
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        rv_follow.layoutManager = LinearLayoutManager(context)
        rv_follow.adapter = userAdapter
        showLoading(true)
    }

    private fun setupViewModel(result: Array<String>) {
        followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)
        followViewModel.setUserList(result)
        followViewModel.getUserList().observe(this.viewLifecycleOwner, Observer { items ->
            if (items != null) userAdapter.mUser = items
        })
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressbarFollow.visibility = View.VISIBLE
        } else {
            progressbarFollow.visibility = View.INVISIBLE
        }
    }
}
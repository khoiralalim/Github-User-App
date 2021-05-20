package com.alimmanurung.submission.view

import User
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alimmanurung.submission.R
import com.alimmanurung.submission.adapter.UserAdapter
import com.alimmanurung.submission.db.DatabaseContract.UsersColumn.Companion.CONTENT_URI
import com.alimmanurung.submission.db.UserHelper
import com.alimmanurung.submission.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.fragment_follow.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var userAdapter: UserAdapter
    private lateinit var userHelper: UserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.favuser)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()
        loadDataSQLite()
        showListData()
    }

    override fun onRestart() {
        super.onRestart()
        loadDataSQLite()
        showListData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadDataSQLite() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredUser = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val users = deferredUser.await()
            if (users.size > 0) userAdapter.mUser = users
            else userAdapter.mUser = ArrayList()
            showLoading(false)
        }
    }

    private fun showListData() {
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        rv_userfav.layoutManager = LinearLayoutManager(this)
        rv_userfav.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val mIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.EXTRA_USER, data)
                startActivity(mIntent)
                showLoading(true)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressbarFavorite.visibility = View.VISIBLE
        } else {
            progressbarFavorite.visibility = View.INVISIBLE
        }
    }
}
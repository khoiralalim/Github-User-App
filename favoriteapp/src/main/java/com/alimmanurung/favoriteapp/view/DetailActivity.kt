package com.alimmanurung.favoriteapp.view

import User
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.alimmanurung.favoriteapp.R
import com.alimmanurung.favoriteapp.adapter.FollowAdapter
import com.alimmanurung.favoriteapp.db.DatabaseContract.UsersColumn.Companion.CONTENT_URI
import com.alimmanurung.favoriteapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "data"
    }

    private lateinit var uriWithId: Uri
    private lateinit var followAdapter: FollowAdapter
    private lateinit var userData: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.DetailUser)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val userDataIntent = intent.getParcelableExtra(EXTRA_USER) as User
        followAdapter = FollowAdapter(this, userDataIntent.uname!!)
        view_pager.adapter = followAdapter
        val tabTitle = resources.getStringArray(R.array.tab_title)
        TabLayoutMediator(tab_detail, view_pager) { tab, posisi ->
            tab.text = tabTitle[posisi]
        }.attach()
        loadDataSQLite(userDataIntent.id!!)
    }

    private fun loadDataSQLite(id: Int) {
        uriWithId = Uri.parse("$CONTENT_URI/$id")
        val cursor = contentResolver.query(uriWithId, null, null, null, null)
        if (cursor != null) {
            val user = MappingHelper.mapCursorToObject(cursor)
            bind(user)
            cursor.close()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun bind(userData: User) {
        Glide.with(this)
            .load(userData.avatar)
            .into(avatar)
        tv_name.text = userData.name
        tv_uname.text = userData.uname
    }
}
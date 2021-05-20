package com.alimmanurung.submission.view

import User
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.alimmanurung.submission.R
import com.alimmanurung.submission.adapter.FollowAdapter
import com.alimmanurung.submission.db.DatabaseContract
import com.alimmanurung.submission.db.DatabaseContract.UsersColumn.Companion.CONTENT_URI
import com.alimmanurung.submission.db.UserHelper
import com.alimmanurung.submission.helper.MappingHelper
import com.alimmanurung.submission.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "data"
    }
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var followerAdapter: FollowAdapter
    private lateinit var userHelper: UserHelper
    private lateinit var userData: User
    private lateinit var uriWithId: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.DetailUser)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()

        val userDataIntent = intent.getParcelableExtra(EXTRA_USER) as User
        userDetailViewModel(userDataIntent.uname!!)
        followerAdapter = FollowAdapter(this, userDataIntent.uname!!)
        view_pager.adapter = followerAdapter
        val tabTitle = resources.getStringArray(R.array.tab_title)
        TabLayoutMediator(tab_detail, view_pager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
        loadDataSQLite(userDataIntent.id!!)
    }

    private fun loadDataSQLite(id: Int) {
        uriWithId = Uri.parse("$CONTENT_URI/$id")
        val cursor = contentResolver.query(uriWithId, null, null, null, null)
        if (cursor != null) {
            updateUserFavorite(MappingHelper.mapCursorToArrayList(cursor), id)
            cursor.close()
        }
    }

    private fun updateUserFavorite(users: ArrayList<User>, id: Int) {
        if (users.size > 0) btn_favorite.setImageResource(R.drawable.baseline_favorite_white_18dp)
        else btn_favorite.setImageResource(R.drawable.baseline_favorite_border_white_18dp)
        btn_favorite.setOnClickListener {
            if (users.size > 0) {
                contentResolver.delete(uriWithId, null, null)
                Toast.makeText(
                    this@DetailActivity,
                    getString(R.string.DeleteFav),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val value = ContentValues()
                value.put(DatabaseContract.UsersColumn._ID, userData.id)
                value.put(DatabaseContract.UsersColumn.COLUMN_NAME_UNAME, userData.uname)
                value.put(DatabaseContract.UsersColumn.COLUMN_NAME_NAME, userData.name)
                value.put(DatabaseContract.UsersColumn.COLUMN_NAME_AVATAR, userData.avatar)
                value.put(DatabaseContract.UsersColumn.COLUMN_NAME_COMP, userData.comp)
                value.put(DatabaseContract.UsersColumn.COLUMN_NAME_LOC, userData.loc)
                value.put(DatabaseContract.UsersColumn.COLUMN_NAME_REP, userData.rep)
                contentResolver.insert(CONTENT_URI, value)
                Toast.makeText(
                    this@DetailActivity,
                    getString(R.string.addfav),
                    Toast.LENGTH_SHORT
                ).show()
            }
            loadDataSQLite(id)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun userDetailViewModel(username: String) {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        detailViewModel.setUserDetail(username)

        detailViewModel.getUserDetail().observe(this, Observer {
            if (it != null) {
                this.userData = it
                Glide.with(this)
                    .load(it.avatar)
                    .into(avatar)
                tv_name.text = it.name
                tv_uname.text = it.uname
            }
        })
    }
}
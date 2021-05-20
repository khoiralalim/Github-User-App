package com.alimmanurung.favoriteapp.view

import User
import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alimmanurung.favoriteapp.R
import com.alimmanurung.favoriteapp.adapter.UserAdapter
import com.alimmanurung.favoriteapp.db.DatabaseContract.UsersColumn.Companion.CONTENT_URI
import com.alimmanurung.favoriteapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var userAdapter: UserAdapter
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.favorite)
        showRecyclerListViewUser()
        loadUserFavoritesAsync()

        if (savedInstanceState == null) {
            loadUserFavoritesAsync()
        } else {
            val userList = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (userList != null) {
                userAdapter.mUser = userList
            }
        }

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val mObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadUserFavoritesAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, mObserver)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, userAdapter.mUser)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadUserFavoritesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredUser = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val users = deferredUser.await()
            if (users.size > 0) userAdapter.mUser = users
            else userAdapter.mUser = ArrayList()
        }
    }

    private fun showRecyclerListViewUser() {
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val mIntent = Intent(this@MainActivity, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.EXTRA_USER, data)
                startActivity(mIntent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_language) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}

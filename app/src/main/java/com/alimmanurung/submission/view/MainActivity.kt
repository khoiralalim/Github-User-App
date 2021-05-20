package com.alimmanurung.submission.view

import User
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alimmanurung.submission.R
import com.alimmanurung.submission.adapter.UserAdapter
import com.alimmanurung.submission.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = resources.getString(R.string.app_name)
        setupViewModel()
        showRecyclerListViewUser()
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

    private fun setupViewModel() {
        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModel::class.java
        )

        search_uname.addTextChangedListener {
            userViewModel.setUserList(it.toString())
            showLoading(true)
        }

        userViewModel.getUserList().observe(this, Observer {
            if (it != null) userAdapter.mUser = it
            showLoading(false)
        })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }
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
        else if (item.itemId == R.id.menu_settings) {
            val mIntent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(mIntent)
        }
        else if (item.itemId == R.id.menu_favorite) {
            val mIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}
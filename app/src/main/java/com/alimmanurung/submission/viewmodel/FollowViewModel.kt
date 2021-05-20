package com.alimmanurung.submission.viewmodel

import User
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alimmanurung.submission.BuildConfig
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowViewModel : ViewModel() {
    private val listUser = MutableLiveData<ArrayList<User>>()
    internal fun getUserList(): LiveData<ArrayList<User>> = listUser

    internal fun setUserList(data: Array<String>) {
        val client = AsyncHttpClient()
        val listItemUser = ArrayList<User>()
        val url = if (data[0].toInt() == 0) "https://api.github.com/users/${data[1]}/followers"
        else "https://api.github.com/users/${data[1]}/following"
//        BuildConfig.GITHUB_TOKEN;
        client.addHeader("Authorization", "token ea645a91bd7f1f8828dd82546bff8f4926a59011")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray
            ) {
                try {
                    listItemUser.clear()
                    val result = String(responseBody)
                    val items = JSONArray(result)
                    for (i in 0 until items.length()) {
                        val user = items.getJSONObject(i)
                        val item = User(
                            user.getLong("id").toInt(),
                            user.getString("login"),
                            null,
                            user.getString("avatar_url"),
                            null,
                            null,
                            null
                        )
                        listItemUser.add(item)
                        listUser.postValue(listItemUser)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
}
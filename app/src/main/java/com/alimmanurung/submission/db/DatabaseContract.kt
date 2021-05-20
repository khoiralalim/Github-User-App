package com.alimmanurung.submission.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.alimmanurung.submission"
    const val SCHEME = "content"

    class UsersColumn : BaseColumns {
        companion object {
            const val TABLE_NAME = "tbgithubuser"
            const val _ID = "_id"
            const val COLUMN_NAME_UNAME = "uname"
            const val COLUMN_NAME_NAME = "name"
            const val COLUMN_NAME_AVATAR = "avatar"
            const val COLUMN_NAME_COMP = "comp"
            const val COLUMN_NAME_LOC = "loc"
            const val COLUMN_NAME_REP = "rep"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}
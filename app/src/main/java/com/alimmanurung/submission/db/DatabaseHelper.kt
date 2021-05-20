package com.alimmanurung.submission.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.alimmanurung.submission.db.DatabaseContract.UsersColumn.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbgithubuser"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.UsersColumn._ID} INTEGER PRIMARY KEY," +
                " ${DatabaseContract.UsersColumn.COLUMN_NAME_UNAME} TEXT NO NULL," +
                " ${DatabaseContract.UsersColumn.COLUMN_NAME_NAME} TEXT NOT NULL," +
                " ${DatabaseContract.UsersColumn.COLUMN_NAME_AVATAR} TEXT NOT NULL," +
                " ${DatabaseContract.UsersColumn.COLUMN_NAME_COMP} TEXT NOT NULL," +
                " ${DatabaseContract.UsersColumn.COLUMN_NAME_LOC} TEXT NOT NULL," +
                " ${DatabaseContract.UsersColumn.COLUMN_NAME_REP} INTEGER NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
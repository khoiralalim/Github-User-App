package com.alimmanurung.submission.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.alimmanurung.submission.db.DatabaseContract.UsersColumn.Companion.TABLE_NAME
import com.alimmanurung.submission.db.DatabaseContract.UsersColumn.Companion._ID
import java.sql.SQLException

class UserHelper(context: Context) {
    companion object {
        private const val DB_TABLE = TABLE_NAME
        private lateinit var dbHelper: DatabaseHelper
        private var INSTANCE: UserHelper? = null
        private lateinit var db: SQLiteDatabase

        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
            }
    }

    init {
        dbHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        db = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()

        if (db.isOpen) db.close()
    }

    fun queryAll(): Cursor {
        return db.query(
            DB_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun queryById(id: String?): Cursor {
        return db.query(
            DB_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return db.insert(DB_TABLE, null, values)
    }

    fun deleteById(id: String?): Int {
        return db.delete(DB_TABLE, "$_ID = '$id'", null)
    }
}
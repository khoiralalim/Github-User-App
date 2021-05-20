package  com.alimmanurung.favoriteapp.helper

import User
import android.database.Cursor
import com.alimmanurung.favoriteapp.db.DatabaseContract

object MappingHelper {

    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<User> {
        val listUsers = ArrayList<User>()

        userCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UsersColumn._ID))
                val uname = getString(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_UNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_NAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_AVATAR))
                val comp = getString(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_COMP))
                val loc = getString(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_LOC))
                val rep = getInt(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_REP))

                listUsers.addAll(listOf(User(id, uname, name, avatar, comp, loc, rep)))
            }
        }
        return listUsers
    }

    fun mapCursorToObject(userCursor: Cursor?): User {
        var user = User(null, null, null, null, null, null, null)

        userCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.UsersColumn._ID))
            val uname = getString(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_UNAME))
            val name = getString(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_NAME))
            val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_AVATAR))
            val comp = getString(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_COMP))
            val loc = getString(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_LOC))
            val rep = getInt(getColumnIndexOrThrow(DatabaseContract.UsersColumn.COLUMN_NAME_REP))
            user = User(id, uname, name, avatar, comp, loc, rep)
        }
        return user
    }
}
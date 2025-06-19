package com.example.flagquiz

class FlagDao {

    fun getRandom5Flag(dbHelper: DBHelper): ArrayList<Flag> {
        val flagList = ArrayList<Flag>()
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM flag ORDER BY RANDOM() LIMIT 5", null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val image = cursor.getString(cursor.getColumnIndex("image"))
            flagList.add(Flag(id, name, image))
        }

        cursor.close()
        db.close()
        return flagList
    }

    fun getRandom3WrongFlag(dbHelper: DBHelper, correctFlagId: Int): ArrayList<Flag> {
        val flagList = ArrayList<Flag>()
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM flag WHERE id != $correctFlagId ORDER BY RANDOM() LIMIT 3", null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val image = cursor.getString(cursor.getColumnIndex("image"))
            flagList.add(Flag(id, name, image))
        }

        cursor.close()
        db.close()
        return flagList
    }
}
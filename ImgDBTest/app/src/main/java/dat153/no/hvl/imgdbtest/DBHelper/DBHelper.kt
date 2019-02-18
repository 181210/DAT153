package dat153.no.hvl.imgdbtest.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteQueryBuilder
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import dat153.no.hvl.imgdbtest.Model.Character

class DBHelper (context: Context) : SQLiteAssetHelper(context, DB_NAME, null, DB_VER){
    companion object {
        private val DB_NAME = "SaveBitMap.db"
        private val DB_VER = 1

        private val TBL_NAME = "Gallery"
        private val COL_NAME = "Name"
        private val COL_DATA = "Data"
        private val COL_ID = "ID"
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Delete old version
        db?.execSQL("DROP TABLE IF EXISTS " + TBL_NAME)
        //Create db
        onCreate(db)
    }


    //CREATE
    @Throws (SQLiteException::class)
    fun addBitmap(name: String, image: ByteArray){
        val database = this.writableDatabase
        val cv = ContentValues()

        cv.put(COL_NAME, name)
        cv.put(COL_DATA, image)

        database.insert(TBL_NAME, null, cv)
    }

    //READ IMG
    fun getBitmapByName(name:String) : ByteArray? {
        val db = this.writableDatabase
        val qb = SQLiteQueryBuilder()

        val sqlSelect = arrayOf(COL_DATA)

        qb.tables = TBL_NAME
        val c = qb.query(db, sqlSelect, "Name = ?", arrayOf(name), null, null, null)

        var result: ByteArray? = null
        if(c.moveToFirst()){
            do {
                result = c.getBlob(c.getColumnIndex(COL_DATA))
            } while (c.moveToNext())
        }

        c.close()
        return result
    }

    //READ NAME
    fun readCharacterNames() : ArrayList<Character>{
        var db = this.readableDatabase
        var list :ArrayList<Character> = ArrayList()

        var selectAll = "SELECT * FROM " + TBL_NAME

        var cursor = db.rawQuery(selectAll, null)

        if (cursor.moveToFirst()){
            do {
                var char = Character()
                char.name = cursor.getString(cursor.getColumnIndex(COL_NAME)).toString()

                list.add(char)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    //UPDATE
    fun updateName(character: Character) : Int{
        var db = writableDatabase

        var values: ContentValues = ContentValues()

        values.put(COL_NAME, character.name)

        return db.update(TBL_NAME, values, COL_NAME + "=?", arrayOf(character.name))
    }

    //DELETE
    fun deleteChar(character: Character){
        var db = writableDatabase
        db.delete(TBL_NAME, COL_NAME + "=?", arrayOf(character.name))
    }

    fun clearDB(){
        writableDatabase.execSQL("DROP TABLE IF EXISTS $TBL_NAME")
    }

    fun getDBItemCount() : Int{
        var db = readableDatabase
        var countQuery = "SELECT * FROM " + TBL_NAME
        var cursor = db.rawQuery(countQuery, null)

        return cursor.count
    }
}
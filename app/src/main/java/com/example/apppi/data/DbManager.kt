package com.example.apppi.data

import DbHelper
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbManager(context: Context) {
    private val helper = DbHelper(context)
    val db: SQLiteDatabase = helper.writableDatabase

    fun addApiKey(apiName: String, apiKey: String) {
        val values = ContentValues()
        values.put(DbHelper.COLUMN_API_NAME, apiName)
        values.put(DbHelper.COLUMN_API_KEY, apiKey)
        db.insert(DbHelper.TABLE_NAME, null, values)
    }


    fun getApiKeys(): Map<String, String> {
        val cursor = db.query(DbHelper.TABLE_NAME, arrayOf(DbHelper.COLUMN_API_NAME, DbHelper.COLUMN_API_KEY), null, null, null, null, null)
        val keys = mutableMapOf<String, String>()
        if(cursor.moveToFirst()) {
            do {
                val apiName = cursor.getString(cursor.run { getColumnIndex(DbHelper.COLUMN_API_NAME) })
                val apiKey = cursor.getString(cursor.run {getColumnIndex(DbHelper.COLUMN_API_KEY) })
                keys[apiName] = apiKey
            } while (cursor.moveToNext())
        }
        return keys
    }

    fun getApis(): List<String>{
        val cursor = db.query(DbHelper.TABLE_NAME, arrayOf(DbHelper.COLUMN_API_NAME), null, null, null, null, null)
        val apis = mutableListOf<String>()
        if(cursor.moveToFirst()) {
            do {
                apis.add(cursor.getString(cursor.run {getColumnIndex(DbHelper.COLUMN_API_NAME) }))
            } while (cursor.moveToNext())
        }
        return apis
    }

    fun getApiKey(apiName: String): String{
        val cursor = db.query(DbHelper.TABLE_NAME, arrayOf(DbHelper.COLUMN_API_KEY), apiName, null, null, null, null)
        cursor.moveToFirst()
        return cursor.getString(cursor.run {getColumnIndex(DbHelper.COLUMN_API_NAME) })
    }

    companion object {
        @Volatile private var instance: DbManager? = null
        fun getInstance(requireContext: Context): DbManager {
            return instance ?: synchronized(this) {
                instance ?: DbManager(requireContext).also { instance = it }
            }
        }
    }

}
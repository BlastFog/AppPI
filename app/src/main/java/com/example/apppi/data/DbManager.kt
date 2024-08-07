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
        db.insert(DbHelper.KEY_TABLE_NAME, null, values)
    }


    fun getApiKeys(): Map<String, String> {
        val cursor = db.query(DbHelper.KEY_TABLE_NAME, arrayOf(DbHelper.COLUMN_API_NAME, DbHelper.COLUMN_API_KEY), null, null, null, null, null)
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

    fun addQueryToFragment(fragment: FragmentDataObject, query: String) {
        val values = ContentValues().apply {
            put(DbHelper.COLUMN_FRAGMENT_QUERY, query)
        }

        val whereClause = "${DbHelper.COLUMN_FRAGMENT_NAME} = ?"
        val whereArgs = arrayOf(fragment.name)

        db.update(DbHelper.FRAGMENT_TABLE_NAME, values, whereClause, whereArgs)
    }

    fun getApis(): List<String>{
        val cursor = db.query(DbHelper.KEY_TABLE_NAME, arrayOf(DbHelper.COLUMN_API_NAME), null, null, null, null, null)
        val apis = mutableListOf<String>()
        if(cursor.moveToFirst()) {
            do {
                apis.add(cursor.getString(cursor.run {getColumnIndex(DbHelper.COLUMN_API_NAME) }))
            } while (cursor.moveToNext())
        }
        return apis
    }


    fun getApiKey(apiName: String): String{
        val selection = "${DbHelper.COLUMN_API_NAME} = ?"

        val cursor = db.query(DbHelper.KEY_TABLE_NAME, arrayOf(DbHelper.COLUMN_API_KEY), selection, arrayOf(apiName), null, null, null)

        var apiKey: String = ""
        if (cursor != null && cursor.moveToFirst()) {
            val apiKeyIndex = cursor.getColumnIndex(DbHelper.COLUMN_API_KEY)
            if (apiKeyIndex != -1) {
                apiKey = cursor.getString(apiKeyIndex)
            }
            cursor.close()
        }

        return apiKey
    }

    fun updateApiKey(apiName: String, newApiKey: String) {
        val whereClause = "${DbHelper.COLUMN_API_NAME} = ?"
        db.update(DbHelper.KEY_TABLE_NAME, ContentValues().apply {
            this.put(DbHelper.COLUMN_API_KEY, newApiKey)
        }, whereClause, arrayOf(apiName))
    }

    fun addFragment(fragment: FragmentDataObject) {
        val values = ContentValues().apply {
            put(DbHelper.COLUMN_FRAGMENT_NAME, fragment.name)
            put(DbHelper.COLUMN_FRAGMENT_URL, fragment.url)
            put(DbHelper.COLUMN_FRAGMENT_KEY, fragment.key)
            put(DbHelper.COLUMN_FRAGMENT_RAW, fragment.raw)
            put(DbHelper.COLUMN_FRAGMENT_NESTED, fragment.nested)
            put(DbHelper.COLUMN_FRAGMENT_QUERY, "")
        }
        db.insert(DbHelper.FRAGMENT_TABLE_NAME, null, values)
    }

    fun removeFragment(fragment: FragmentDataObject) {
        val whereClause = "${DbHelper.COLUMN_FRAGMENT_NAME} = ? AND ${DbHelper.COLUMN_FRAGMENT_URL} = ? AND ${DbHelper.COLUMN_FRAGMENT_KEY} = ? AND ${DbHelper.COLUMN_FRAGMENT_RAW} = ? AND ${DbHelper.COLUMN_FRAGMENT_NESTED} = ?"
        val whereArgs = arrayOf(
            fragment.name,
            fragment.url,
            if (fragment.key) "1" else "0",
            if (fragment.raw) "1" else "0",
            fragment.nested
        )

        db.delete(DbHelper.FRAGMENT_TABLE_NAME, whereClause, whereArgs)
    }



    fun getFragments(): List<FragmentDataObject> {
        val cursor = db.query(DbHelper.FRAGMENT_TABLE_NAME, arrayOf(DbHelper.COLUMN_FRAGMENT_NAME, DbHelper.COLUMN_FRAGMENT_URL, DbHelper.COLUMN_FRAGMENT_KEY, DbHelper.COLUMN_FRAGMENT_RAW, DbHelper.COLUMN_FRAGMENT_NESTED, DbHelper.COLUMN_FRAGMENT_QUERY), null, null, null, null, null)
        val fragments = mutableListOf<FragmentDataObject>()

        if(cursor != null && cursor.moveToFirst()) {
            do {
                val fragment = FragmentDataObject()
                fragment.name = cursor.getString(cursor.run {getColumnIndex(DbHelper.COLUMN_FRAGMENT_NAME)})
                fragment.url = cursor.getString(cursor.run {getColumnIndex(DbHelper.COLUMN_FRAGMENT_URL)})
                fragment.key = cursor.getInt(cursor.run {getColumnIndex(DbHelper.COLUMN_FRAGMENT_KEY)}) > 0
                fragment.raw = cursor.getInt(cursor.run {getColumnIndex(DbHelper.COLUMN_FRAGMENT_RAW)}) > 0
                fragment.nested = cursor.getString(cursor.run {getColumnIndex(DbHelper.COLUMN_FRAGMENT_NESTED)})
                fragment.queries = cursor.getString(cursor.run {getColumnIndex(DbHelper.COLUMN_FRAGMENT_QUERY)})
                fragments.add(fragment)
            } while(cursor.moveToNext())
        }

        return fragments
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
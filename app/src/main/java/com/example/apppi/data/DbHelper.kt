import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "api_keys"
        private const val DATABASE_VERSION = 15

        const val FRAGMENT_TABLE_NAME = "fragments"
        const val COLUMN_FRAGMENT_NAME = "fragment_name"
        const val COLUMN_FRAGMENT_URL = "fragment_url"
        const val COLUMN_FRAGMENT_RAW = "fragment_raw"
        const val COLUMN_FRAGMENT_KEY = "fragment_key"
        const val COLUMN_FRAGMENT_NESTED = "fragment_nested"
        const val COLUMN_FRAGMENT_QUERY = "fragment_query"

        const val KEY_TABLE_NAME = "apis"
        const val COLUMN_ID = "id"
        const val COLUMN_API_NAME = "api_name"
        const val COLUMN_API_KEY = "api_key"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $KEY_TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_API_NAME TEXT, $COLUMN_API_KEY TEXT)")

        db?.execSQL("CREATE TABLE IF NOT EXISTS $FRAGMENT_TABLE_NAME " +
                "($COLUMN_FRAGMENT_NAME TEXT PRIMARY KEY, " +
                "$COLUMN_FRAGMENT_URL TEXT, $COLUMN_FRAGMENT_RAW BOOLEAN, " +
                "$COLUMN_FRAGMENT_KEY BOOLEAN, $COLUMN_FRAGMENT_NESTED TEXT," +
                "$COLUMN_FRAGMENT_QUERY TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $KEY_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $FRAGMENT_TABLE_NAME")
        onCreate(db)
    }

}
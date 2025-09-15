////import android.content.ContentValues
////import android.content.Context
////import android.database.sqlite.SQLiteDatabase
////import android.database.sqlite.SQLiteOpenHelper
//
//class ReminderDbHelper(context: Context) :
//    SQLiteOpenHelper(context, "reminders.db", null, 1) {
//
//    override fun onCreate(db: SQLiteDatabase) {
//        db.execSQL(
//            """
//            CREATE TABLE reminders (
//                id INTEGER PRIMARY KEY AUTOINCREMENT,
//                reminderData INTEGER NOT NULL,
//                reminderTable TEXT NOT NULL,
//                reminderDateTime INTEGER NOT NULL
//            )
//            """.trimIndent()
//        )
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        // Neste caso, apenas descarta e recria
//        db.execSQL("DROP TABLE IF EXISTS reminders")
//        onCreate(db)
//    }
//}
//
//data class Reminder(
//    val id: Long = 0,
//    val reminderData: Int,
//    val reminderTable: String,
//    val reminderDateTime: Long
//)
//
//class ReminderRepository(
////    private val context: Context
//) {
//    private val dbHelper = ReminderDbHelper(context)
//
//    fun insert(reminder: Reminder): Long {
//        val db = dbHelper.writableDatabase
//        val values = ContentValues().apply {
//            put("reminderData", reminder.reminderData)
//            put("reminderTable", reminder.reminderTable)
//            put("reminderDateTime", reminder.reminderDateTime)
//        }
//        return db.insert("reminders", null, values)
//    }
//
//    fun getAll(): List<Reminder> {
//        val db = dbHelper.readableDatabase
//        val cursor = db.query(
//            "reminders",
//            arrayOf("id", "reminderData", "reminderTable", "reminderDateTime"),
//            null, null, null, null, "reminderDateTime ASC"
//        )
//        val reminders = mutableListOf<Reminder>()
//        with(cursor) {
//            while (moveToNext()) {
//                val reminder = Reminder(
//                    id = getLong(getColumnIndexOrThrow("id")),
//                    reminderData = getInt(getColumnIndexOrThrow("reminderData")),
//                    reminderTable = getString(getColumnIndexOrThrow("reminderTable")),
//                    reminderDateTime = getLong(getColumnIndexOrThrow("reminderDateTime"))
//                )
//                reminders.add(reminder)
//            }
//            close()
//        }
//        return reminders
//    }
//
//    fun deleteById(id: Long): Int {
//        val db = dbHelper.writableDatabase
//        return db.delete("reminders", "id = ?", arrayOf(id.toString()))
//    }
//
//    fun update(reminder: Reminder): Int {
//        val db = dbHelper.writableDatabase
//        val values = ContentValues().apply {
//            put("reminderData", reminder.reminderData)
//            put("reminderTable", reminder.reminderTable)
//            put("reminderDateTime", reminder.reminderDateTime)
//        }
//        return db.update("reminders", values, "id = ?", arrayOf(reminder.id.toString()))
//    }
//}

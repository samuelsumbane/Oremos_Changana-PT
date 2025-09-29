package com.samuel.oremoschanganapt

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ReminderDbHelper(context: Context) :
    SQLiteOpenHelper(context, "reminders.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE reminders (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                reminderData INTEGER NOT NULL,
                reminderTable TEXT NOT NULL,
                reminderDateTime INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS reminders")
        onCreate(db)
    }
}






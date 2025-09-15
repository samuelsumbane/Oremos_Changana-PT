package com.samuelsumbane.oremoscatolico.repository

import com.samuelsumbane.oremoscatolico.Reminder
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import kotlin.use


class ReminderDbHelper(private val databasePath: String = "reminders.db") {

    init {
        initializeDatabase()
    }

    private fun getConnection(): Connection {
        return DriverManager.getConnection("jdbc:sqlite:$databasePath")
    }

    private fun initializeDatabase() {
        getConnection().use { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeUpdate(
                    """
                    CREATE TABLE IF NOT EXISTS reminders (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        reminderData INTEGER NOT NULL,
                        reminderTable TEXT NOT NULL,
                        reminderDateTime INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }
    }

    fun insert(reminder: Reminder): Long {
        getConnection().use { conn ->
            conn.prepareStatement(
                "INSERT INTO reminders (reminderData, reminderTable, reminderDateTime) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            ).use { stmt ->
                stmt.setInt(1, reminder.reminderData)
                stmt.setString(2, reminder.reminderTable)
                stmt.setLong(3, reminder.reminderDateTime)

                stmt.executeUpdate()

                val generatedKeys = stmt.generatedKeys
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1)
                }
            }
        }
        return -1
    }

    fun getAll(): List<Reminder> {
        val reminders = mutableListOf<Reminder>()

        getConnection().use { conn ->
            conn.createStatement().use { stmt ->
                val rs = stmt.executeQuery(
                    "SELECT id, reminderData, reminderTable, reminderDateTime FROM reminders ORDER BY reminderDateTime ASC"
                )

                while (rs.next()) {
                    reminders.add(
                        Reminder(
                            id = rs.getLong("id"),
                            reminderData = rs.getInt("reminderData"),
                            reminderTable = rs.getString("reminderTable"),
                            reminderDateTime = rs.getLong("reminderDateTime")
                        )
                    )
                }
            }
        }
        return reminders
    }

    fun deleteById(id: Long): Int {
        getConnection().use { conn ->
            conn.prepareStatement("DELETE FROM reminders WHERE id = ?").use { stmt ->
                stmt.setLong(1, id)
                return stmt.executeUpdate()
            }
        }
    }

    fun update(reminder: Reminder): Int {
        getConnection().use { conn ->
            conn.prepareStatement(
                "UPDATE reminders SET reminderData = ?, reminderTable = ?, reminderDateTime = ? WHERE id = ?"
            ).use { stmt ->
                stmt.setInt(1, reminder.reminderData)
                stmt.setString(2, reminder.reminderTable)
                stmt.setLong(3, reminder.reminderDateTime)
                stmt.setLong(4, reminder.id)

                return stmt.executeUpdate()
            }
        }
    }

    fun close() {
        // Conexões são fechadas automaticamente pelo use{}, mas você pode adicionar lógica adicional se necessário
    }
}
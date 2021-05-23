package com.example.isuscorp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.isuscorp.data.dao.TicketDao
import com.example.isuscorp.data.dao.UserDao
import com.example.isuscorp.data.model.Ticket
import com.example.isuscorp.data.model.User
import com.example.isuscorp.misc.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [User::class, Ticket::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class IsusCorpDB : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun ticketDao(): TicketDao

    private class IsusCorpDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    database.userDao()
                    database.ticketDao().getAllTickets()
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: IsusCorpDB? = null

        fun getDatabase(context: Context,scope: CoroutineScope): IsusCorpDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IsusCorpDB::class.java,
                    "database"
                )
                    .addCallback(IsusCorpDatabaseCallback(scope))
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
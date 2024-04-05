package com.example.cupcake.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


/**
 * Room database class for storing order-related data.
 */

@Database(entities = [OrderEntity::class], version = 3, exportSchema = false)
@TypeConverters(ListConverter::class) // Add this line to specify the converter
abstract class OrderDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: OrderDatabase? = null

        fun getDatabase(context: Context): OrderDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderDatabase::class.java,
                    "order_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
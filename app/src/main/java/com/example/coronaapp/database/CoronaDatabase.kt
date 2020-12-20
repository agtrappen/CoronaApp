package com.example.coronaapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CoronaSymptoms::class], version = 1, exportSchema = false)
abstract class CoronaDatabase : RoomDatabase() {
    abstract val coronaSymptomsDao: CoronaSymptomsDao

    companion object {
        @Volatile
        private var INSTANCE: CoronaDatabase? = null

        fun getInstance(context: Context) : CoronaDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            CoronaDatabase::class.java,
                            "corona_symptoms"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
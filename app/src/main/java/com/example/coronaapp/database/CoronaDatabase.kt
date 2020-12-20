package com.example.coronaapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CoronaSymptoms::class], version = 1, exportSchema = false)
abstract class CoronaDatabase : RoomDatabase() {
    abstract val coronaSymptomsDao: CoronaSymptomsDao

    companion object {
        @Volatile
        private var INSTANCE: CoronaDatabase? = null

        fun getInstance(context: Context) : CoronaDatabase {
            synchronized(this) {
                var instance = INSTANCE
                Log.d("Test", "Hey!")


                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            CoronaDatabase::class.java,
                            "corona"
                    )
                            .fallbackToDestructiveMigration()
                            .addCallback(CALLBACK)
                            .allowMainThreadQueries()
                            .build()
                    INSTANCE = instance
                }

                return instance
            }
        }

        private val CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                Log.d("Test", "WORKS")
                super.onCreate(db)
                db.execSQL("DELETE FROM symptoms")
                db.execSQL("INSERT INTO symptoms (symptomId, corona_symptom) VALUES (1, 'koorts')")
                db.execSQL("INSERT INTO symptoms (symptomId, corona_symptom) VALUES (2, 'droge hoest')")
                db.execSQL("INSERT INTO symptoms (symptomId, corona_symptom) VALUES (3, 'vermoeidheid')")
                db.execSQL("INSERT INTO symptoms (symptomId, corona_symptom) VALUES (4, 'pijn in het lichaam')")
            }
        }
    }
}
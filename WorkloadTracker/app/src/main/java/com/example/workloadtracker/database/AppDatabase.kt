package com.example.workloadtracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workloadtracker.database.dao.*
import com.example.workloadtracker.enteties.*

@Database(
    entities = [
        Discipline::class,
        EducationForm::class,
        GroupCode::class,
        Lecturer::class,
        LessonType::class,
        Plan::class,
        Rate::class,
        Workload::class
    ],
    version = AppDatabase.DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    //region Dao

    abstract fun disciplineDao(): DisciplineDao

    abstract fun educationFormDao(): EducationFormDao

    abstract fun groupCodeDao(): GroupCodeDao

    abstract fun lecturerDao(): LecturerDao

    abstract fun lessonTypeDao(): LessonTypeDao

    abstract fun planDao(): PlanDao

    abstract fun rateDao(): RateDao

    abstract fun workloadDao(): WorkloadDao

    companion object {

        private const val DB_NAME = "workloads.sqlite"
        internal const val DB_RELATIVE_PATH = "/databases/$DB_NAME"

        /**
         * Current database version
         */
        internal const val DB_VERSION = 1

        fun createInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                context.filesDir.toString() + DB_RELATIVE_PATH
            ).allowMainThreadQueries().build()
        }
    }
}
package com.example.schoolappace1122.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.schoolappace1122.database.SchoolsDAO
import com.example.schoolappace1122.model.SchoolInfoResponse

@Database(
    entities = [SchoolInfoResponse::class],
    version = 1
)

abstract class SchoolsDatabase: RoomDatabase() {
    abstract fun getSchoolsDAO(): SchoolsDAO
}
package com.example.nycschoolsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.nycschoolsapp.database.SchoolsDatabase
import com.example.nycschoolsapp.database.SchoolsDAO
import com.example.nycschoolsapp.rest.SchoolsRepository
import com.example.nycschoolsapp.utils.SchoolsViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

/**
 * [Class] - Module that provides application context
 */

@Module
class ApplicationModule(private val application: Application) {

    /**
     * Method that provides application context
     */
    @Provides
    fun provideContext(): Context = application.applicationContext

    /**
     * Method that provides Database
     */
    @Provides
    fun providesSchoolsDatabase(context: Context): SchoolsDatabase =
        Room.databaseBuilder(
            context,
            SchoolsDatabase::class.java,
            "schools-db"
        ).build()

    /**
     * Method that provides DAO
     */
    @Provides
    fun providesSchoolDAO(
        schoolsDatabase: SchoolsDatabase
    ): SchoolsDAO =
        schoolsDatabase.getSchoolsDAO()

    /**
     * Method that provides View Model Factory
     */
    @Provides
    fun provideViewModelFactory(
        repository: SchoolsRepository,
        ioDispatcher: CoroutineDispatcher
    ): SchoolsViewModelFactory =
        SchoolsViewModelFactory(repository,ioDispatcher)

}
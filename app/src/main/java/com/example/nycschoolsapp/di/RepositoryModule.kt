package com.example.nycschools.di

import com.example.nycschoolsapp.rest.SchoolsRepository
import com.example.nycschoolsapp.rest.SchoolsRepositoryImpl
import dagger.Binds
import dagger.Module

/**
 * [Abstract Class] - Module for repository
 */

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideSchoolsRepository(
        schoolsRepositoryImpl: SchoolsRepositoryImpl
    ): SchoolsRepository

}
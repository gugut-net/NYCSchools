package com.example.nycschoolsapp.di

import com.example.nycschools.di.RepositoryModule
import com.example.nycschoolsapp.MainActivity
import com.example.nycschools.utils.BaseFragment
import dagger.Component

/**
 * [Interface] - specifies the Dependency Injection components
 */

@Component(modules = [
    NetworkModule::class,
    RepositoryModule::class,
    ApplicationModule::class
])
interface SchoolsComponent {

    /**
     * Method to inject dependencies in the Main Activity
     */
    fun inject(mainActivity: MainActivity)

    /**
     * Method to inject dependencies in the Base Fragment
     */
    fun inject(baseFragment: BaseFragment)

}
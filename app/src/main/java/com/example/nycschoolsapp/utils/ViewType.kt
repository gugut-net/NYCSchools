package com.example.nycschoolsapp.utils

import com.example.nycschoolsapp.view.adapter.SchoolsDataAdapter

sealed class ViewType{

    data class LETTER(val letter: String): ViewType()
    data class SCHOOLS_DATA(val schoolsData: SchoolsDataAdapter): ViewType()

}

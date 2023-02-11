package com.example.schoolappace1122.utils

import com.example.schoolappace1122.view.adapter.SchoolsDataAdapter

sealed class ViewType{

    data class LETTER(val letter: String): ViewType()
    data class SCHOOLS_DATA(val schoolsData: SchoolsDataAdapter): ViewType()

}

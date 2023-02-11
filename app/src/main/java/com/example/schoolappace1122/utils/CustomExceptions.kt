package com.example.schoolappace1122.utils

class NullSchoolsException(message: String = "Schools Response is Null"): Exception(message)
class NullSatResultsException(message: String = "SAT Response is Null"): Exception(message)
class FailureResponseException(message: String?): Exception(message)
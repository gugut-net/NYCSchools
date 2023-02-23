package com.example.nycschoolsapp.rest

import com.example.nycschoolsapp.utils.UIState
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * For Unit testing make sure to provide everything needed
 */
class SchoolRepositoryImplTest {

    /**
     * one way of USING @MocK annotation
     */
//    @MockK(relaxed = true)
//    private lateinit var mockServiceApi: SchoolsAPI

    /**
     * Another way of USING MockK
     */
    private val mockServiceApi: SchoolsAPI = mockk(relaxed = true, relaxUnitFun = true)

    private lateinit var testUnitObject: SchoolsRepository



    @Before
    fun setUp() {
        /**
         * MockKAnnotations.init is called when we initialized the MocK annotation
         */
        //MockKAnnotations.init()

        testUnitObject = SchoolRepositoryImpl(mockServiceApi)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
    /**
     * AAA
     * what does the triple A stands for Assignment, Action, Assertion
     */
    @Test
    fun `get all schools response is success and a full list is provided returns success state`() = runTest {
        /**
         * ASSIGNMENT
         */
//        val mockResponse = mockk<Response<List<NYCSchool>>>()
        coEvery {
            mockServiceApi.getSchools()
        } returns mockk() {
            every { isSuccessful } returns true
            every { body() } returns listOf(mockk(), mockk(), mockk())

        }
        val states = mutableListOf<UIState<List<NYCSchoolResponse>>>()

        /**
         * ACTION
         */
        testUnitObject.getAllSchools().collect {
            states.add(it)

        }
        /**
         * Assertion
         */
        assertThat(states).hasSize(2)
        assertThat(states.first()).isInstanceOf(UIState.LOADING::class.java)
        assertThat(states[1]).isInstanceOf(UIState.SUCCESS::class.java)

        assertThat((states[1] as UIState.SUCCESS<List<NYCSchoolResponse>>).response).hasSize(3)
    }

    @Test
    fun `get all schools response is success and a full list is provided returns error state`() = runTest {
        /**
         * ASSIGNMENT
         */
        coEvery {
            mockServiceApi.getAllSchools()
        } returns mockk() {
            every { isSuccessful } returns true
                every { body() } returns null
        }
        val states = mutableListOf<UIState<List<NYCSchoolResponse>>>()

        /**
         * Action
         */
        testUnitObject.getAllSchools().collect {
            states.add(it)
        }
        /**
         * Assertion
         */
        assertThat(states).hasSize(2)
        assertThat(states.first()).isInstanceOf(UIState.LOADING::class.java)
        assertThat(states[1]).isInstanceOf(UIState.ERROR::class.java)

        assertThat((states[1] as UIState.ERROR).error.localizedMessage).isEqualTo("Schools are null")
    }

    @Test
    fun `get something using TDD return a success state`() = runTest {
        testUnitObject.getSomething().collect {
            assertThat(it).isInstanceOf(UIState.SUCCESS::class.java)
        }
    }
}
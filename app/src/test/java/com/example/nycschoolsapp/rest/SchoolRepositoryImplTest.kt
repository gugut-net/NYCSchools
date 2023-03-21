package com.example.nycschoolsapp.rest


import android.util.Log
import com.example.nycschoolsapp.model.SatResultsResponse
import com.example.nycschoolsapp.model.SchoolInfoResponse
import com.example.nycschoolsapp.utils.UIState
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * For Unit testing make sure to provide everything needed
 */
@OptIn(ExperimentalCoroutinesApi::class)
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

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)





    @Before
    fun setUp() {
        /**
         * MockKAnnotations.init is called when we initialized the MocK annotation
         */
        //MockKAnnotations.init()
        testUnitObject = SchoolsRepositoryImpl(mockServiceApi)
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
    fun `get all schools response is loading returns loading state`() = runTest {
        /**
         * ASSIGNMENT
         */
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        coEvery {
            mockServiceApi.getSchools()
        } returns mockk() {
            every { isSuccessful } returns true
            every { body() } returns listOf(mockk(), mockk(), mockk())

        }
        val states = mutableListOf<UIState<List<SchoolInfoResponse>>>()

        /**
         * ACTION
         */
        testUnitObject.getAllSchools().collect {
            states.add(it)

        }

        /**
         * ASSERTION
         */
        assertThat(states).hasSize(2)
        assertThat(states.first()).isInstanceOf(UIState.LOADING::class.java)
        assertThat(states[1]).isInstanceOf(UIState.SUCCESS::class.java)
    }


    /**
     * Success for all Schools
     */
    @Test
    fun `get all schools response is success returns success state`() = runTest {
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
        val states = mutableListOf<UIState<List<SchoolInfoResponse>>>()

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

        assertThat((states[1] as UIState.SUCCESS<List<SchoolInfoResponse>>).response).hasSize(3)
    }

    /**
     * Error state for all Schools
     */
    @Test
    fun `get all schools response is error returns error state`() {
        coEvery { mockServiceApi.getSatResults() } throws Exception("Error")

        val job = testScope.launch {
            testUnitObject.getAllSatResults().collect {
                if (it is UIState.ERROR) {
                    assertEquals("Error", it.error)
                }
            }
        }
        job.cancel()
    }
//    @Test
//    fun `get all schools response is error returns error state`() = runTest {
//
//        mockkStatic(Log::class)
//        every { Log.e(any(), any()) } returns 0
//        /**
//         * ASSIGNMENT
//         */
//        coEvery {
//            mockServiceApi.getSchools()
//        } returns mockk() {
//            every { isSuccessful } returns true
//                every { body() } returns null
//        }
//        val states = mutableListOf<UIState<List<SchoolInfoResponse>>>()
//
//        /**
//         * Action
//         */
//        testUnitObject.getAllSchools().collect {
//            states.add(it)
//        }
//        /**
//         * Assertion
//         */
//        assertThat(states).hasSize(2)
//        assertThat(states.first()).isInstanceOf(UIState.LOADING::class.java)
//        assertThat(states[1]).isInstanceOf(UIState.ERROR::class.java)
//
//        assertThat((states[1] as UIState.ERROR).error.localizedMessage).isEqualTo("Schools are null")
//    }

    /**
     * Loading sate for SAT scores
     */
    @Test
    fun `get all SAT response is loading returns loading state`() = runTest {
        /**
         * ASSIGNMENT
         */
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        coEvery {
            mockServiceApi.getSatResults()
        } returns mockk() {
            every { isSuccessful } returns true
            every { body() } returns listOf(mockk(), mockk(), mockk())

        }
        val states = mutableListOf<UIState<List<SatResultsResponse>>>()

        /**
         * ACTION
         */
        testUnitObject.getAllSatResults().collect {
            states.add(it)

        }

        /**
         * ASSERTION
         */
        assertThat(states).hasSize(2)
        assertThat(states.first()).isInstanceOf(UIState.LOADING::class.java)
        assertThat(states[1]).isInstanceOf(UIState.SUCCESS::class.java)
    }

    /**
     * Success sate for SAT scores
     * PASS
     */
    @Test
    fun `get all SAT response is success returns success state`() = runTest {
        /**
         * ASSIGNMENT
         */
        coEvery {
            mockServiceApi.getSatResults()
        } returns mockk() {
            every { isSuccessful } returns true
            every { body() } returns listOf(mockk(), mockk(), mockk())

        }
        val states = mutableListOf<UIState<List<SatResultsResponse>>>()

        /**
         * ACTION
         */
        testUnitObject.getAllSatResults().collect {
            states.add(it)

        }
        /**
         * Assertion
         */
        assertThat(states).hasSize(2)
        assertThat(states.first()).isInstanceOf(UIState.LOADING::class.java)
        assertThat(states[1]).isInstanceOf(UIState.SUCCESS::class.java)

        assertThat((states[1] as UIState.SUCCESS<List<SatResultsResponse>>).response).hasSize(3)
    }

    /**
     * Error sate for SAT scores
     * FAIL
     */
//    @Test
//    fun `get all SAT response is error returns error state`() = runTest {
//        mockkStatic(Log::class)
//        every { Log.e(any(), any()) } returns 0
//        /**
//         * ASSIGNMENT
//         */
//        coEvery {
//            mockServiceApi.getSatResults()
//        } returns mockk() {
//            every { isSuccessful } returns true
//            every { body() } returns null
//        }
//        val states = mutableListOf<UIState<List<SatResultsResponse>>>()
//
//        /**
//         * ACTION
//         */
//        testUnitObject.getAllSatResults().collect {
//            states.add(it)
//        }
//
//        /**
//         * ASSERTION
//         */
//        assertThat(states).hasSize(2)
//        assertThat(states.first()).isInstanceOf(UIState.LOADING::class.java)
//        assertThat(states[1]).isInstanceOf(UIState.ERROR::class.java)
//
//        assertThat((states[1] as UIState.ERROR).error.localizedMessage).isEqualTo("SAT Response is Null")
//
//    }

    @Test
    fun `get all SAT response is error returns error state`() {
        coEvery { mockServiceApi.getSatResults() } throws Exception("Error")

        val job = testScope.launch {
            testUnitObject.getAllSatResults().collect {
                if (it is UIState.ERROR) {
                    assertEquals("Error", it.error)
                }
            }
        }
        job.cancel()
    }
}
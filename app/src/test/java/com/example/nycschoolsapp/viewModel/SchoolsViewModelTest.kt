package com.example.nycschoolsapp.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nycschoolsapp.rest.SchoolsRepository
import com.example.nycschoolsapp.utils.UIState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.EventObject

@OptIn(ExperimentalCoroutinesApi::class)
class SchoolsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var testObject: SchoolsViewModel

    private val mockRepository = mockk<SchoolsRepository>(relaxed = true)
    private val mockDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mockDispatcher)
        testObject = SchoolsViewModel(mockRepository, mockDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    /**
     * Success state for all schools
     */
    @Test
    fun `get all schools response is success returns success state`() {
        coEvery { mockRepository.getAllSchools() } returns flowOf(
            UIState.SUCCESS(listOf(mockk(), mockk(), mockk()))
        )

        testObject.schoolsInfo.observeForever {
            if (it is UIState.SUCCESS) {
                assertEquals(3, it)
            }
        }
        testObject.getSchools()
    }

    /**
     * Error state for all schools
     */
    @Test
    fun `get all schools response is an error and returns an error state`() {
        coEvery { mockRepository.getAllSchools() } returns flowOf(
            UIState.ERROR(Exception("Failed to retrieve schools list"))
        )

        testObject.schoolsInfo.observeForever {
            if (it is UIState.ERROR) {
                assertEquals("Failed to retrieve schools list", it.error.message)
            }
        }
        testObject.getSchools()
    }

    /**
     * Loading state for all schools
     */
    @Test
    fun `get all schools response is loading returns loading state`() {
        coEvery { mockRepository.getAllSchools() } returns flowOf(UIState.LOADING)

        testObject.schoolsInfo.observeForever {
            if (it is UIState.LOADING) {
                assertEquals(UIState.LOADING, it)
            }
        }
        testObject.getSchools()
    }

    /**
     * Success state for all SAT scores
     */
    @Test
    fun `get all SAT results response is success returns success state`() {
        coEvery { mockRepository.getAllSatResults() } returns flowOf(
            UIState.SUCCESS(listOf(mockk(), mockk(), mockk()))
        )

        testObject.satResults.observeForever {
            if (it is UIState.SUCCESS) {
                assertEquals(3, it.response.size)
            }
        }
        testObject.getSatResults()
    }

    /**
     * Loading state for all SAT scores
     */
    @Test
    fun `get all SAT results response is loading returns loading state`() {
        coEvery { mockRepository.getAllSatResults() } returns flowOf(
            UIState.LOADING
        )

        testObject.satResults.observeForever {
            if (it is UIState.LOADING) {
                assertEquals(UIState.LOADING, it)
            }
        }
        testObject.getSatResults()
    }

    /**
     * Error state for all SAT scores
     */
    @Test
    fun `get all sat results response is error returns error state`() {
        val error = Exception("Failed to get sat results")
        coEvery { mockRepository.getAllSatResults() } returns flowOf(
            UIState.ERROR(error)
        )

        testObject.satResults.observeForever {
            if (it is UIState.ERROR) {
                assertEquals(error, it.error)
            }
        }
        testObject.getSatResults()
    }

}
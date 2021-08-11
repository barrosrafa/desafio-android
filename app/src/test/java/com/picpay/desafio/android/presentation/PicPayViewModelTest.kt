package com.picpay.desafio.android.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.utils.MainCoroutineRule
import com.picpay.desafio.android.utils.await
import com.picpay.desafio.android.utils.verify
import com.nhaarman.mockitokotlin2.*
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.presentation.mapper.PicPayPresentation
import com.picpay.desafio.android.presentation.model.UserPresentation
import com.picpay.desafio.android.presentation.viewmodel.PicPayViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class PicPayViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val useCase: GetUsersUseCase = mock()
    private val viewModel: PicPayViewModel by lazy {
        PicPayViewModel(useCase, Dispatchers.Default)
    }

    @Test
    fun `get users when is first page should return list`() = runBlockingTest {
        // Given
        whenever(useCase.invoke()).thenReturn(mockResponseSuccess())

        // When
        viewModel.getUsers()

        // Then
        verify(useCase).invoke()
        viewModel.loadingEvent.verify()
        viewModel.successResponseEvent.verify {
            assertEquals(1, it!!.data[0].id)
            assertEquals("teste", it.data[0].name)
            assertEquals("teste", it.data[0].img)
            assertEquals("teste", it.data[0].username)
        }
    }

    @Test
    fun `get users when is the last page should return empty list`() = runBlockingTest {
        // Given
        whenever(useCase.invoke()).thenReturn(mockResponseSuccess())
        whenever(useCase.invoke()).thenReturn(mockResponseEmptySuccess())

        // When
        viewModel.getUsers()
        viewModel.successResponseEvent.await(50L)

        // Then
        verify(useCase, times(2)).invoke()
        viewModel.loadingEvent.verify()
        viewModel.scrollLoadingEvent.verify()
        viewModel.fullResultResponseEvent.verify()
    }

    @Test
    fun `get users when is the last page should return error`() = runBlockingTest {
        // Given
        whenever(useCase.invoke()).thenReturn(mockResponseSuccess())
        whenever(useCase.invoke()).thenReturn(PicPayPresentation.ErrorResponse)

        // When
        viewModel.getUsers()
        viewModel.successResponseEvent.await(50L)

        // Then
        verify(useCase, times(2)).invoke()
        viewModel.loadingEvent.verify()
        viewModel.scrollLoadingEvent.verify()
        viewModel.showToast.verify()
    }

    @Test
    fun `get users when already load and not scrolling should not call use case`() =
        runBlockingTest {
            // Given
            whenever(useCase.invoke()).thenReturn(mockResponseSuccess())

            // When
            viewModel.getUsers()
            viewModel.successResponseEvent.await(50L)
            viewModel.getUsers()

            // Then
            verify(useCase).invoke()
            viewModel.successResponseEvent.verify()
        }

    private fun mockResponseSuccess(): PicPayPresentation {
        return PicPayPresentation.SuccessResponse(
            listOf(
                UserPresentation(
                    id = 1,
                    name = "teste",
                    username = "teste",
                    img = "teste"
                )
            )
        )
    }

    private fun mockResponseEmptySuccess() = PicPayPresentation.EmptyResponse
}
package com.picpay.desafio.android.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.picpay.desafio.android.utils.MainCoroutineRule
import com.picpay.desafio.android.utils.await
import com.picpay.desafio.android.utils.verify
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.presentation.mapper.PicPayPresentation
import com.picpay.desafio.android.presentation.model.UserPresentation
import com.picpay.desafio.android.presentation.viewmodel.PicPayViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

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
}
package com.picpay.desafio.android.data.datasource

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.picpay.desafio.android.data.api.PicPayService
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.model.UserDomain
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val service: PicPayService = mock()
    private val dataSource: RemoteDataSource = RemoteDataSourceImpl(service)

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `list users should return the list from remote data source`() = runBlockingTest {
        // Given
        whenever(service.getUsers()).thenReturn(mockResponse())

        // When
        val result = dataSource.getUsers()

        // Then
        assertEquals(result, expectedResponse())
        assertEquals(result?.get(0)?.id, 12)
    }

    private fun mockResponse() =
        Response.success(
            listOf(
                User(
                    id = 12,
                    name = "teste",
                    username = "teste",
                    img = "teste"
                )
            )
        )

    private fun expectedResponse() =
        listOf(
            UserDomain(
                id = 12,
                name = "teste",
                username = "teste",
                img = "teste"
            )
        )
}
package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.api.PicPayService
import com.picpay.desafio.android.data.api.retrofit.HttpClient
import com.picpay.desafio.android.data.api.retrofit.RetrofitClient
import com.picpay.desafio.android.data.repository.PicPayRepositoryImpl
import com.picpay.desafio.android.data.datasource.RemoteDataSource
import com.picpay.desafio.android.data.datasource.RemoteDataSourceImpl
import com.picpay.desafio.android.domain.repository.PicPayRepository
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.presentation.viewmodel.PicPayViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

val domainModules = module {
    factory { GetUsersUseCase(User = get()) }
}

val presentationModules = module {
    viewModel { PicPayViewModel(getUsersUseCase = get(), dispatcher = Dispatchers.IO) }
}

val dataModules = module {
    factory<RemoteDataSource> { RemoteDataSourceImpl(service = get()) }
    factory<PicPayRepository> { PicPayRepositoryImpl(remoteDataSource = get()) }
}

val anotherModules = module {
    single { RetrofitClient(application = androidContext(), BASE_URL).newInstance() }
    single { HttpClient(get()) }
    factory { get<HttpClient>().create(PicPayService::class.java) }
}
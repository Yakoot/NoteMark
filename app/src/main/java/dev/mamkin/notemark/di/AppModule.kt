package dev.mamkin.notemark.di

import dev.mamkin.notemark.core.data.datastore.TokenDataStore
import dev.mamkin.notemark.core.data.datastore.tokenDataStore
import dev.mamkin.notemark.core.data.networking.HttpClientFactory
import dev.mamkin.notemark.login.presentation.login.LoginViewModel
import dev.mamkin.notemark.main.data.networking.RemoteAuthDataSource
import dev.mamkin.notemark.main.domain.AuthDataSource
import dev.mamkin.notemark.register.presentation.register.RegisterViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create(), get()) }
//    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()
    singleOf(::RemoteAuthDataSource).bind<AuthDataSource>()
    singleOf(::TokenDataStore)

    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
//    viewModelOf(::CoinListViewModel)
}
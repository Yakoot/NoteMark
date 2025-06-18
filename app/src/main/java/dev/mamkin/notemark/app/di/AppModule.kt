package dev.mamkin.notemark.app.di

import dev.mamkin.notemark.app.NoteMarkApp
import dev.mamkin.notemark.app.navigation.MainScreenViewModel
import dev.mamkin.notemark.auth.data.networking.RemoteAuthDataSource
import dev.mamkin.notemark.auth.domain.AuthDataSource
import dev.mamkin.notemark.core.data.datastore.TokenDataStore
import dev.mamkin.notemark.core.data.datastore.UserProfileDataStore
import dev.mamkin.notemark.core.data.networking.HttpClientFactory
import dev.mamkin.notemark.login.presentation.login.LoginViewModel
import dev.mamkin.notemark.notes.data.KtorRemoteNotesDataSource
import dev.mamkin.notemark.notes.domain.RemoteNotesDataSource
import dev.mamkin.notemark.notes.presentation.notes.NotesViewModel
import dev.mamkin.notemark.register.presentation.register.RegisterViewModel
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as NoteMarkApp).applicationScope
    }
    single { HttpClientFactory.create(CIO.create(), get()) }
    singleOf(::RemoteAuthDataSource).bind<AuthDataSource>()
    singleOf(::KtorRemoteNotesDataSource).bind<RemoteNotesDataSource>()
    singleOf(::TokenDataStore)
    singleOf(::UserProfileDataStore)

    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::NotesViewModel)
    viewModelOf(::MainScreenViewModel)
}
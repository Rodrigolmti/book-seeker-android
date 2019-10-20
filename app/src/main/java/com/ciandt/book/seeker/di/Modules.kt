package com.ciandt.book.seeker.di

import com.ciandt.book.seeker.domain.datasource.AppLocalDataSource
import com.ciandt.book.seeker.domain.datasource.AppRemoteDataSource
import com.ciandt.book.seeker.domain.datasource.LocalDataSource
import com.ciandt.book.seeker.domain.datasource.RemoteDataSource
import com.ciandt.book.seeker.domain.repository.Repository
import com.ciandt.book.seeker.domain.use_case.*
import com.ciandt.book.seeker.service.remote.provideBookWebService
import com.ciandt.book.seeker.service.remote.provideDefaultOkHttpClient
import com.ciandt.book.seeker.service.remote.provideRetrofit
import com.ciandt.book.seeker.service.repository.AppRepository
import com.ciandt.book.seeker.storage.database.AppDatabase
import com.ciandt.book.seeker.storage.database.AppDatabaseImpl
import com.ciandt.book.seeker.ui.book_detail.BookDetailViewModel
import com.ciandt.book.seeker.ui.book_store.BookStoreViewModel
import com.ciandt.book.seeker.ui.library.LibraryViewModel
import com.ciandt.book.seeker.ui.reading_now.ReadingNowViewModel
import com.ciandt.book.seeker.ui.search.SearchViewModel
import com.ciandt.book.seeker.ui.search_result.SearchResultViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule: Module = module {
    single<AppDatabase> { AppDatabaseImpl(get()) }
    single<LocalDataSource> { AppLocalDataSource(get()) }
    single<RemoteDataSource> { AppRemoteDataSource(get()) }
    single<Repository> { AppRepository(get(), get()) }
}

val useCaseModule: Module = module {
    factory { SearchBooksUseCase(get(), get()) }
    factory { GetQueryUseCase(get()) }
    factory { SaveSearchedQueryUseCase(get(), get()) }
    factory { ClearHistoricUseCase(get()) }
    factory { UpdateQueryUseCase(get()) }
    factory { ClearBookUseCase(get()) }
    factory { GetBookUseCase(get()) }
    factory { GetHistoricUseCase(get()) }
    factory { UpdateBookUseCase(get()) }
    factory { ClearQueryUseCase(get()) }
}

val networkModule: Module = module {
    single { provideDefaultOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideBookWebService(get()) }
}

val viewModelModule: Module = module {
    viewModel { SearchViewModel(get(), get(), get(), get()) }
    viewModel { SearchResultViewModel(get(), get(), get()) }
    viewModel { BookDetailViewModel(get(), get()) }
    viewModel { ReadingNowViewModel() }
    viewModel { LibraryViewModel() }
    viewModel { BookStoreViewModel() }
}


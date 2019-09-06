package oliveira.fabio.searchmovie.di

import oliveira.fabio.searchmovie.data.remote.api.MovieApi
import oliveira.fabio.searchmovie.data.remote.config.provideApi
import org.koin.dsl.module

val remoteModule = module {
    single { provideApi(MovieApi::class.java) }
}
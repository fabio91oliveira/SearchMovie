package oliveira.fabio.searchmovie.di

import oliveira.fabio.searchmovie.data.local.source.Database
import oliveira.fabio.searchmovie.data.local.source.provideBuilder
import org.koin.dsl.module

val daoModule = module {
    single { provideBuilder(get()) }
    single { get<Database>().getMovieDao() }
}
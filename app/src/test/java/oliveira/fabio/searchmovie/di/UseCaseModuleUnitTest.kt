package oliveira.fabio.searchmovie.di

import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.data.repository.FavoriteMoviesListRepository
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.domain.usecase.FavoriteMoviesListUseCase
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.data.repository.MoviesListRepository
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.domain.usecase.MoviesListUseCase
import oliveira.fabio.searchmovie.feature.moviedetails.data.repository.MovieDetailsRepository
import oliveira.fabio.searchmovie.feature.moviedetails.domain.usecase.MovieDetailsUseCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.Mockito

class UseCaseModuleUnitTest : AutoCloseKoinTest() {

    @Before
    fun setUp() {
        val repositoryModule = module {
            single<MoviesListRepository> { Mockito.mock(MoviesListRepository::class.java) }
            single<MovieDetailsRepository> { Mockito.mock(MovieDetailsRepository::class.java) }
            single<FavoriteMoviesListRepository> { Mockito.mock(
                FavoriteMoviesListRepository::class.java) }
        }
        startKoin { modules(arrayListOf(repositoryModule, useCaseModule)) }
    }

    @Test
    fun `Assert that MoviesListUseCase is provided by module`() {
        val moviesListUseCase by inject<MoviesListUseCase>()
        Assert.assertNotNull(moviesListUseCase)
    }

    @Test
    fun `Assert that MovieDetailsUseCase is provided by module`() {
        val movieDetailsUseCase by inject<MovieDetailsUseCase>()
        Assert.assertNotNull(movieDetailsUseCase)
    }


    @Test
    fun `Assert that FavoriteMoviesListUseCase is provided by module`() {
        val favoriteMoviesListUseCase by inject<FavoriteMoviesListUseCase>()
        Assert.assertNotNull(favoriteMoviesListUseCase)
    }

}
package oliveira.fabio.searchmovie.di

import oliveira.fabio.searchmovie.data.local.dao.MovieDao
import oliveira.fabio.searchmovie.data.remote.api.MovieApi
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.data.repository.FavoriteMoviesListRepository
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.data.repository.MoviesListRepository
import oliveira.fabio.searchmovie.feature.moviedetails.data.repository.MovieDetailsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.Mockito

class RepositoryModuleUnitTest : AutoCloseKoinTest() {

    @Before
    fun setUp() {
        val apiModule = module {
            single<MovieApi> { Mockito.mock(MovieApi::class.java) }
            single<MovieDao> { Mockito.mock(MovieDao::class.java) }
        }
        startKoin { modules(arrayListOf(apiModule, repositoryModule)) }
    }

    @Test
    fun `Assert that MoviesListRepository is provided by module`() {
        val moviesListRepository by inject<MoviesListRepository>()
        Assert.assertNotNull(moviesListRepository)
    }

    @Test
    fun `Assert that MovieDetailsRepository is provided by module`() {
        val movieDetailsRepository by inject<MovieDetailsRepository>()
        Assert.assertNotNull(movieDetailsRepository)
    }


    @Test
    fun `Assert that FavoriteMoviesListRepository is provided by module`() {
        val favoriteMoviesListRepository by inject<FavoriteMoviesListRepository>()
        Assert.assertNotNull(favoriteMoviesListRepository)
    }


}
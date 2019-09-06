package oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Flowable
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.domain.usecase.MoviesListUseCase
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.state.MoviesListState
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito

class MoviesListViewModelUnitTest : AutoCloseKoinTest() {

    @Rule
    @JvmField
    val testSchedulerRule = InstantTaskExecutorRule()

    private lateinit var moviesListUseCase: MoviesListUseCase
    private lateinit var moviesListViewModel: MoviesListViewModel

    @Before
    fun setUp() {
        moviesListUseCase = Mockito.mock(MoviesListUseCase::class.java)
        moviesListViewModel = MoviesListViewModel(moviesListUseCase)
    }

    @Test
    fun `Should test if moviesList is successState`() {
        val list = listOf<MovieEntity>()
        val listFlowable = Flowable.fromCallable { list }
        Mockito.`when`(moviesListUseCase.fetchMovies()).thenReturn(listFlowable)

        moviesListViewModel.getMovies()

        Assert.assertEquals(
            MoviesListState.ShowSuccess(list),
            moviesListViewModel.moviesListState.value
        )
    }

    @Test
    fun `Should test if moviesList is sorted`() {
        val list = mutableListOf<MovieEntity>().apply {
            val movieOne = MovieEntity()
            movieOne.date = "2017-08-09"

            val movieTwo = MovieEntity()
            movieOne.date = "2018-08-09"

            add(movieOne)
            add(movieTwo)
        }.toList()

        val listFlowable = Flowable.fromCallable { list }
        Mockito.`when`(moviesListUseCase.sortMoviesList(list)).thenReturn(listFlowable)

        moviesListViewModel.sortMovies(list)

        Assert.assertEquals(
            MoviesListState.ShowSuccess(list),
            moviesListViewModel.moviesListState.value
        )
    }

}
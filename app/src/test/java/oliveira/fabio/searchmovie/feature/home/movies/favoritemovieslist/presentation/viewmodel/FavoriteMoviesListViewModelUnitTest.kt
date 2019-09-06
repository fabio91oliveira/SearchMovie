package oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Flowable
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.domain.usecase.FavoriteMoviesListUseCase
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.state.FavoriteMoviesListState
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito

class FavoriteMoviesListViewModelUnitTest : AutoCloseKoinTest() {

    @Rule
    @JvmField
    val testSchedulerRule = InstantTaskExecutorRule()

    private lateinit var favoriteMoviesListUseCase: FavoriteMoviesListUseCase
    private lateinit var favoriteMoviesListViewModel: FavoriteMoviesListViewModel

    @Before
    fun setUp() {
        favoriteMoviesListUseCase = Mockito.mock(FavoriteMoviesListUseCase::class.java)
        favoriteMoviesListViewModel = FavoriteMoviesListViewModel(favoriteMoviesListUseCase)
    }

    @Test
    fun `Should test if favoriteMoviesList is empty state`() {
        val list = Flowable.fromCallable { listOf<MovieEntity>() }
        Mockito.`when`(favoriteMoviesListUseCase.fetchFavoriteMovies()).thenReturn(list)

        favoriteMoviesListViewModel.getFavoriteMovies()

        Assert.assertEquals(
            FavoriteMoviesListState.ShowEmptyState,
            favoriteMoviesListViewModel.favoriteMoviesListState.value
        )
    }

    @Test
    fun `Should test if favoriteMoviesList is showSuccess state`() {
        val list = mutableListOf<MovieEntity>().apply {
            add(MovieEntity())
        }
        val listFlowable = Flowable.fromCallable { list.toList() }
        Mockito.`when`(favoriteMoviesListUseCase.fetchFavoriteMovies()).thenReturn(listFlowable)

        favoriteMoviesListViewModel.getFavoriteMovies()

        Assert.assertEquals(
            FavoriteMoviesListState.ShowSuccess(list),
            favoriteMoviesListViewModel.favoriteMoviesListState.value
        )
    }

}
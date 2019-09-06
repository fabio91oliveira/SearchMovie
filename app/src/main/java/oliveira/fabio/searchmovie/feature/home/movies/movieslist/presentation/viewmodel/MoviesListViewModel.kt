package oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.domain.usecase.MoviesListUseCase
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.state.MoviesListState
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.state.MoviesLoadingState

class MoviesListViewModel(private val moviesListUseCase: MoviesListUseCase) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _loadingState by lazy { MutableLiveData<MoviesLoadingState>() }
    private val _moviesListState by lazy { MutableLiveData<MoviesListState>() }

    val moviesLoadingState: LiveData<MoviesLoadingState>
        get() = _loadingState

    val moviesListState: LiveData<MoviesListState>
        get() = _moviesListState

    fun getMovies() {
        _loadingState.postValue(MoviesLoadingState.ShowLoading)
        compositeDisposable.add(
            moviesListUseCase.fetchMovies()
                .subscribe({
                    _moviesListState.postValue(MoviesListState.ShowSuccess(it))
                    _loadingState.postValue(MoviesLoadingState.HideLoading)
                }, {
                    _moviesListState.postValue(MoviesListState.ShowError(it))
                    _loadingState.postValue(MoviesLoadingState.HideLoading)
                })
        )
    }

    fun sortMovies(movieEntityList: List<MovieEntity>) {
        _loadingState.postValue(MoviesLoadingState.ShowLoading)
        compositeDisposable.add(
            moviesListUseCase.sortMoviesList(movieEntityList)
                .subscribe({
                    _moviesListState.postValue(MoviesListState.ShowSuccess(it))
                    _loadingState.postValue(MoviesLoadingState.HideLoading)
                }, {
                    _moviesListState.postValue(MoviesListState.ShowError(it))
                    _loadingState.postValue(MoviesLoadingState.HideLoading)
                })
        )
    }

    fun onDestroy() {
        onCleared()
        with(compositeDisposable) {
            if (isDisposed.not()) dispose()
        }
    }
}
package oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.domain.usecase.FavoriteMoviesListUseCase
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.state.FavoriteMoviesListState
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.state.FavoriteMoviesLoadingState

class FavoriteMoviesListViewModel(private val favoriteMoviesListUseCase: FavoriteMoviesListUseCase) :
    ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _loadingState by lazy { MutableLiveData<FavoriteMoviesLoadingState>() }
    private val _favoriteMoviesListState by lazy { MutableLiveData<FavoriteMoviesListState>() }

    val loadingState: LiveData<FavoriteMoviesLoadingState>
        get() = _loadingState

    val favoriteMoviesListState: LiveData<FavoriteMoviesListState>
        get() = _favoriteMoviesListState

    fun getFavoriteMovies() {
        _loadingState.postValue(FavoriteMoviesLoadingState.ShowLoading)
        compositeDisposable.add(
            favoriteMoviesListUseCase.fetchFavoriteMovies()
                .subscribe({
                    when (it.isEmpty().not()) {
                        true -> _favoriteMoviesListState.postValue(
                            FavoriteMoviesListState.ShowSuccess(
                                it
                            )
                        )
                        false -> _favoriteMoviesListState.postValue(FavoriteMoviesListState.ShowEmptyState)
                    }

                    _loadingState.postValue(FavoriteMoviesLoadingState.HideLoading)
                }, {
                    _favoriteMoviesListState.postValue(FavoriteMoviesListState.ShowError(it))
                    _loadingState.postValue(FavoriteMoviesLoadingState.HideLoading)
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
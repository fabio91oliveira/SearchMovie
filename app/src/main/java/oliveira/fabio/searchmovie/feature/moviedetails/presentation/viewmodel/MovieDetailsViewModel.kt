package oliveira.fabio.searchmovie.feature.moviedetails.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity
import oliveira.fabio.searchmovie.feature.moviedetails.domain.usecase.MovieDetailsUseCase
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.state.FavoriteLoadingState
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.state.FavoriteState

class MovieDetailsViewModel(private val movieDetailsUseCase: MovieDetailsUseCase) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _favoriteLoadingState by lazy { MutableLiveData<FavoriteLoadingState>() }
    val favoriteLoadingState: LiveData<FavoriteLoadingState>
        get() = _favoriteLoadingState

    private val _favoriteState by lazy { MutableLiveData<FavoriteState>() }
    val favoriteState: LiveData<FavoriteState>
        get() = _favoriteState

    fun isFavorite(movieEntity: MovieEntity) {
        _favoriteLoadingState.postValue(FavoriteLoadingState.ShowLoading)
        compositeDisposable.add(
            movieDetailsUseCase.isFavoriteMovie(movieEntity)
                .subscribe({
                    it?.also {
                        _favoriteState.postValue(FavoriteState.ShowFavorite(true))
                    } ?: run {
                        _favoriteState.postValue(FavoriteState.ShowFavorite(false))
                    }
                    _favoriteLoadingState.postValue(FavoriteLoadingState.HideLoading)
                }, {
                    _favoriteState.postValue(FavoriteState.ShowFavorite(false))
                    _favoriteLoadingState.postValue(FavoriteLoadingState.HideLoading)
                })
        )
    }

    fun addFavorite(movieEntity: MovieEntity?, isFavorite: Boolean) {
        movieEntity?.also { movie ->
            when (isFavorite) {
                true -> {
                    compositeDisposable.add(
                        movieDetailsUseCase.addFavorite(movie)
                            .subscribe({
                                _favoriteState.postValue(FavoriteState.ShowFavorite(isFavorite))
                            }, {
                                _favoriteState.postValue(FavoriteState.ShowError(it))
                            })
                    )
                }
                false -> {
                    compositeDisposable.add(
                        movieDetailsUseCase.removeFavorite(movie)
                            .subscribe({
                                _favoriteState.postValue(FavoriteState.ShowFavorite(isFavorite))
                            }, {
                                _favoriteState.postValue(FavoriteState.ShowError(it))
                            })
                    )
                }
            }
        }
    }

    fun validateOverview(str: String?, emptyString: String): String {
        str?.also {
            return if (it.isEmpty()) emptyString else it
        }
        return emptyString
    }

    fun onDestroy() {
        onCleared()
        with(compositeDisposable) {
            if (isDisposed.not()) dispose()
        }
    }
}
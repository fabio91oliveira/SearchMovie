package oliveira.fabio.searchmovie.feature.moviedetails.presentation.activity

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_movie_details.*
import oliveira.fabio.searchmovie.R
import oliveira.fabio.searchmovie.base.enums.DatePatternEnum
import oliveira.fabio.searchmovie.base.enums.ImageQualityEnum
import oliveira.fabio.searchmovie.base.extensions.*
import oliveira.fabio.searchmovie.base.presentation.BaseActivity
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.state.FavoriteLoadingState
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.state.FavoriteState
import oliveira.fabio.searchmovie.feature.moviedetails.presentation.viewmodel.MovieDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsActivity : BaseActivity() {

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()
    private val movieEntity by lazy { intent?.extras?.getParcelable<MovieEntity>(MOVIE_TAG) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        movieDetailsViewModel.onDestroy()
    }

    private fun init() {
        initView()
        setupToolbar()
        populateScreen()
        initLiveDatas()
        initClickListener()
        validateFavoriteMovie()
    }

    private fun initView() = setContentView(R.layout.activity_movie_details)

    private fun setupToolbar() {
        with(toolbar) {
            setSupportActionBar(this)
            setNavigationOnClickListener { finish() }
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun populateScreen() {
        movieEntity?.apply {
            imgMovie.loadImage(ImageQualityEnum.ORIGINAL.getUrlWithQualityPrefix() + backdropImageUrl)
            txtMovie.text = movieName
            txtDate.text = date?.toDate(DatePatternEnum.REGULAR_PATTERN.pattern)
                ?.formatToViewDateDefaults(DatePatternEnum.PT_BR_PATTERN.pattern)
            txtAbout.text = movieDetailsViewModel.validateOverview(
                overview,
                resources.getString(R.string.no_information)
            )
        }
    }


    private fun initLiveDatas() {
        with(movieDetailsViewModel) {
            favoriteLoadingState.observe(this@MovieDetailsActivity, Observer {
                when (it) {
                    is FavoriteLoadingState.ShowLoading -> {
                        loading.showView(true)
                    }
                    is FavoriteLoadingState.HideLoading -> {
                        loading.showView(false)
                    }
                }
            })
            favoriteState.observe(this@MovieDetailsActivity, Observer {
                when (it) {
                    is FavoriteState.ShowFavorite -> {
                        with(chkFavorite) {
                            showView(true)
                            isChecked = it.isFavorite
                        }
                    }
                    is FavoriteState.ShowError -> {
                        chkFavorite.showView(false)
                    }
                }
            })
        }
    }

    private fun initClickListener() {
        chkFavorite.setOnClickListener {
            it.doPopAnimation(100, 1.55f) {
                movieDetailsViewModel.addFavorite(movieEntity, chkFavorite.isChecked)
                setResult(Activity.RESULT_OK)
            }
        }
    }

    private fun validateFavoriteMovie() = movieEntity?.also { movieDetailsViewModel.isFavorite(it) }

    companion object {
        const val MOVIE_TAG = "MOVIE"
        const val REQUEST_CODE = 100
    }
}
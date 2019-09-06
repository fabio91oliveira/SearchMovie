package oliveira.fabio.searchmovie.feature.home.movies.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movies.*
import oliveira.fabio.searchmovie.R
import oliveira.fabio.searchmovie.base.enums.DatePatternEnum
import oliveira.fabio.searchmovie.base.enums.ImageQualityEnum
import oliveira.fabio.searchmovie.base.extensions.formatToViewDateDefaults
import oliveira.fabio.searchmovie.base.extensions.loadImage
import oliveira.fabio.searchmovie.base.extensions.toDate
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity

class MoviesAdapter(private val onClickMovieListener: OnClickMovieListener) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var _moviesList: MutableList<MovieEntity> = mutableListOf()
    val moviesList: List<MovieEntity>
        get() = _moviesList


    override fun getItemCount() = _moviesList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(_moviesList[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
    )

    fun addList(moviesList: List<MovieEntity>) {
        this._moviesList.addAll(moviesList)
        notifyDataSetChanged()
    }

    fun clearList() {
        _moviesList.clear()
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(movie: MovieEntity) {
            with(movie) {
                txtMovie.text = "$movieName - $genres"
                imgMovie.loadImage(ImageQualityEnum.ORIGINAL.getUrlWithQualityPrefix() + backdropImageUrl)
                txtDate.text = date?.toDate(DatePatternEnum.REGULAR_PATTERN.pattern)
                    ?.formatToViewDateDefaults(DatePatternEnum.PT_BR_PATTERN.pattern)
                content.setOnClickListener {
                    onClickMovieListener.onClickMovie(this)
                }
            }
        }
    }

    interface OnClickMovieListener {
        fun onClickMovie(movie: MovieEntity)
    }
}
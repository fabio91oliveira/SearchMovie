package oliveira.fabio.searchmovie.data.local.dao

import androidx.room.*
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movieEntity: MovieEntity): Long

    @Delete
    fun deleteMovie(movieEntity: MovieEntity): Int

    @Transaction
    @Query("SELECT * FROM movie")
    fun listMovies(): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE id=:id")
    fun getMovieDetailsById(id: Long): MovieEntity?
}
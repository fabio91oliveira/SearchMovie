package oliveira.fabio.searchmovie.data.local.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import oliveira.fabio.searchmovie.data.local.converter.ListConverter
import oliveira.fabio.searchmovie.data.local.dao.MovieDao
import oliveira.fabio.searchmovie.data.local.entity.MovieEntity

@Database(entities = [(MovieEntity::class)], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}
package oliveira.fabio.searchmovie.data.local.source

import android.content.Context
import androidx.room.Room

fun provideBuilder(context: Context) = Room.databaseBuilder(
    context,
    Database::class.java,
    "SearchMovie.db"
)
    .build()
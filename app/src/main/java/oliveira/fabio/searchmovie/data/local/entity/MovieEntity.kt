package oliveira.fabio.searchmovie.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0,
    var movieName: String? = "",
    var backdropImageUrl: String? = "",
    var posterImageUrl: String? = "",
    var overview: String? = "",
    var date: String? = "",
    var genres: List<String> = listOf()
) : Parcelable
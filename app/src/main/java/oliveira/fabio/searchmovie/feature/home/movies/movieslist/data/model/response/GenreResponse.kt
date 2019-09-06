package oliveira.fabio.searchmovie.feature.home.movies.movieslist.data.model.response

data class GenreResponse(
    val genres: List<Genre> = listOf()
) {
    data class Genre(
        val id: Int = 0,
        val name: String = ""
    )
}
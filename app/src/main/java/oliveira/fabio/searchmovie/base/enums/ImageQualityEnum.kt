package oliveira.fabio.searchmovie.base.enums

import oliveira.fabio.searchmovie.data.remote.config.BASE_URL_IMAGES

enum class ImageQualityEnum(private val prefixQuality: String) {
    PROFILE("h632"),
    BACKDROP("w780"),
    ORIGINAL("original");

    fun getUrlWithQualityPrefix() = BASE_URL_IMAGES + prefixQuality
}
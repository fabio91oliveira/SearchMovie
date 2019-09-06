package oliveira.fabio.searchmovie.base.extensions

import android.content.res.Resources
import java.text.SimpleDateFormat
import java.util.*

fun Float.dpToPx() = this * Resources.getSystem().displayMetrics.density

fun String.toDate(pattern: String): Date? {
    return try {
        SimpleDateFormat(pattern).parse(this)
    } catch (th: Throwable) {
        null
    }
}

fun Date.formatToViewDateDefaults(pattern: String): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}
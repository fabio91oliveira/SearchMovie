package oliveira.fabio.searchmovie.base.extensions

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import oliveira.fabio.searchmovie.R

fun View.showView(hasToShow: Boolean) {
    visibility = if (hasToShow) View.VISIBLE else View.GONE
}

fun AppCompatImageView.loadImage(image: Any?) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(context).load(image)
        .apply(
            RequestOptions.centerCropTransform().priority(Priority.IMMEDIATE).placeholder(
                circularProgressDrawable
            ).error(
                R.color.colorAccent
            ).diskCacheStrategy(
                DiskCacheStrategy.ALL
            )
        )
        .transition(
            DrawableTransitionOptions.withCrossFade()
        ).into(this)
}

fun View.doClickAnimationRotation(rotation: Float) {
    animate()
        .apply {
            this.duration = 350
            rotation(rotation)
            start()
        }
}

fun View.doPopAnimation(duration: Long, scale: Float, func: () -> Unit) {
    animate()
        .apply {
            this.duration = duration
            scaleX(scale)
            scaleY(scale)
            withEndAction {
                this.duration = duration
                scaleX(1f)
                scaleY(1f)
                withEndAction {
                    func.invoke()
                }
            }
            start()
        }
}
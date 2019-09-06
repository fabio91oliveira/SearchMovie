package oliveira.fabio.searchmovie.base.extensions

import io.reactivex.Flowable

fun <T> Flowable<T>.configThread() = subscribeOn(io.reactivex.schedulers.Schedulers.io())
    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
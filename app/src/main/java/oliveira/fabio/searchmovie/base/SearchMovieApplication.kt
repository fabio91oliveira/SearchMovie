package oliveira.fabio.searchmovie.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import oliveira.fabio.searchmovie.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SearchMovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
            modules(
                listOf(
                    remoteModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    daoModule
                )
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
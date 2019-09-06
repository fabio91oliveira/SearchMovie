package oliveira.fabio.searchmovie.di

import android.content.Context
import oliveira.fabio.searchmovie.data.local.dao.MovieDao
import oliveira.fabio.searchmovie.data.local.source.Database
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.Mockito

class DaoModuleUnitTest : AutoCloseKoinTest() {

    @Before
    fun setUp() {
        val appModule = module {
            single<Context> { Mockito.mock(Context::class.java) }
        }
        startKoin { modules(arrayListOf(appModule, daoModule)) }
    }

    @Test
    fun `Assert that Database is provided by module`() {
        val database by inject<Database>()
        Assert.assertNotNull(database)
    }

    @Test
    fun `Assert that MovieDao is provided by module`() {
        val movieDao by inject<MovieDao>()
        Assert.assertNotNull(movieDao)
    }
}
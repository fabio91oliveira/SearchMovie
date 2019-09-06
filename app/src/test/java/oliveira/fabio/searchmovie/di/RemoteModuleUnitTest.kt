package oliveira.fabio.searchmovie.di

import oliveira.fabio.searchmovie.data.remote.api.MovieApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class RemoteModuleUnitTest : AutoCloseKoinTest() {

    @Before
    fun setUp() {
        startKoin { modules(remoteModule) }
    }

    @Test
    fun `Assert that MovieApi is provided by module`() {
        val movieApi by inject<MovieApi>()
        Assert.assertNotNull(movieApi)
    }

}
package oliveira.fabio.searchmovie.feature.home.principal.presentation.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import oliveira.fabio.searchmovie.R
import oliveira.fabio.searchmovie.base.presentation.BaseActivity
import oliveira.fabio.searchmovie.feature.home.about.presentation.fragment.AboutFragment
import oliveira.fabio.searchmovie.feature.home.movies.favoritemovieslist.presentation.fragment.FavoriteMoviesListFragment
import oliveira.fabio.searchmovie.feature.home.movies.movieslist.presentation.fragment.MoviesListFragment

class HomeActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    MoviesListFragment.OnMoviesListChangeListener {

    private lateinit var moviesListFragment: MoviesListFragment
    private lateinit var favoriteMoviesListFragment: FavoriteMoviesListFragment
    private lateinit var aboutFragment: AboutFragment
    private lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initBottomNavigation()

        savedInstanceState?.let {
            initFragments()

            supportFragmentManager.findFragmentByTag(MoviesListFragment::class.java.simpleName)
                ?.let { moviesListFragment = it as MoviesListFragment }
            supportFragmentManager.findFragmentByTag(FavoriteMoviesListFragment::class.java.simpleName)
                ?.let { favoriteMoviesListFragment = it as FavoriteMoviesListFragment }
            supportFragmentManager.findFragmentByTag(AboutFragment::class.java.simpleName)
                ?.let { aboutFragment = it as AboutFragment }


            val tag = savedInstanceState.getString(CURRENT_TAB)
            when (supportFragmentManager.findFragmentByTag(tag)) {
                is MoviesListFragment -> activeFragment =
                    supportFragmentManager.findFragmentByTag(tag) as MoviesListFragment
                is FavoriteMoviesListFragment -> activeFragment =
                    supportFragmentManager.findFragmentByTag(tag) as FavoriteMoviesListFragment
                is AboutFragment -> activeFragment =
                    supportFragmentManager.findFragmentByTag(tag) as AboutFragment
            }

        } ?: run {
            initFragments()
            activeFragment = moviesListFragment
            changeFragment(moviesListFragment)
        }
    }

    override fun onBackPressed() = finish()
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_movies -> {
                changeFragment(moviesListFragment)
                return true
            }
            R.id.navigation_favorite_movies -> {
                changeFragment(favoriteMoviesListFragment)
                return true
            }
            R.id.navigation_about -> {
                changeFragment(aboutFragment)
                return true
            }
        }
        return false
    }

    override fun onListChanged() {
        supportFragmentManager.findFragmentByTag(FavoriteMoviesListFragment::class.java.simpleName)
            ?.let { favoriteMoviesListFragment.listFavoriteMovies() }
    }

    private fun initView() = setContentView(R.layout.activity_home)
    private fun initBottomNavigation() = navigation.setOnNavigationItemSelectedListener(this)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_TAB, activeFragment::class.java.simpleName)
    }

    private fun initFragments() {
        moviesListFragment = MoviesListFragment.newInstance()
        favoriteMoviesListFragment = FavoriteMoviesListFragment.newInstance()
        aboutFragment = AboutFragment.newInstance()
    }

    private fun changeFragment(fragment: Fragment) {
        val tag = fragment::class.java.simpleName
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            if (activeFragment != fragment) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment, tag).addToBackStack(tag).hide(activeFragment)
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment, tag).commit()
            }
        } else {
            supportFragmentManager.beginTransaction().addToBackStack(null).hide(activeFragment)
                .show(fragment).commit()
        }
        activeFragment = fragment
    }

    companion object {
        private const val CURRENT_TAB = "CURRENT_TAB"
    }
}
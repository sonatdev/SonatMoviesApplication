package com.sonat.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sonat.movies.view.MovieDetailsFragment
import com.sonat.movies.view.MoviesFragment

class MainActivity : AppCompatActivity(), MoviesFragment.MovieSelectionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, MoviesFragment())
                .commit()
        }
    }

    override fun onMovieSelected(movieId: Int) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.main_container, MovieDetailsFragment.newInstance(movieId))
            .commit()
    }
}
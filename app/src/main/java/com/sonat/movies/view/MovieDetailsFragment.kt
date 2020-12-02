package com.sonat.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sonat.movies.R

private const val MOVIE_ID_PARAM = "movieId"

class MovieDetailsFragment : Fragment() {
    private var movieId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getString(MOVIE_ID_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movie_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.text_back_btn)
            .setOnClickListener { activity?.onBackPressed() }
    }

    companion object {
        fun newInstance(movieId: String) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_ID_PARAM, movieId)
                }
            }
    }
}
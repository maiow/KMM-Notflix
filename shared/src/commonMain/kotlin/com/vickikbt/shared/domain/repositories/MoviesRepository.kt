package com.vickikbt.shared.domain.repositories

import com.vickikbt.shared.domain.models.Movie
import com.vickikbt.shared.domain.utils.Constants.STARTING_PAGE_INDEX
import com.vickikbt.shared.utils.NetworkResultState
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    /** Fetch Now Playing movies from data source*/
    suspend fun fetchNowPlayingMovies(page: Int = STARTING_PAGE_INDEX): Flow<NetworkResultState<List<Movie>?>>

    /** Fetch Trending movies from data source*/
    suspend fun fetchTrendingMovies(
        mediaType: String = "movie",
        timeWindow: String = "week",
        page: Int = STARTING_PAGE_INDEX
    ): Flow<NetworkResultState<List<Movie>?>>

    /** Fetch Popular movies from data source*/
    suspend fun fetchPopularMovies(page: Int = STARTING_PAGE_INDEX): Flow<NetworkResultState<List<Movie>?>>

    /** Fetch Upcoming movies from data source*/
    suspend fun fetchUpcomingMovies(page: Int = STARTING_PAGE_INDEX): Flow<NetworkResultState<List<Movie>?>>

    /** Get movies based on category from cache*/
    /*@Deprecated("Pending caching implementation")
    suspend fun getMovies(category: String): Flow<List<Movie>>*/

    // Search movie from network source
    suspend fun searchMovie(
        movieName: String,
        page: Int = STARTING_PAGE_INDEX
    ): Flow<NetworkResultState<List<Movie>?>>
}

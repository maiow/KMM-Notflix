package com.vickikbt.shared.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vickikbt.shared.ui.components.MovieCardPortrait
import com.vickikbt.shared.ui.components.MovieRatingSection
import com.vickikbt.shared.presentation.ui.screens.details.DetailsViewModel
import com.vickikbt.shared.ui.components.ItemMovieCast
import com.vickikbt.shared.ui.components.appbars.DetailsV2AppBar
import com.vickikbt.shared.utils.getPopularity
import com.vickikbt.shared.utils.getRating
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsV2Screen(
    navigator: Navigator,
    viewModel: DetailsViewModel = koinInject(),
    movieId: Int
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.getMovieDetails(movieId)
        viewModel.fetchSimilarMovies(movieId)
        viewModel.getMovieCast(movieId)
    }

    val movieDetailsState = viewModel.movieDetailsState.collectAsState().value

    val scrollState = rememberScrollState()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Box(modifier = Modifier.fillMaxSize()) {
        if (movieDetailsState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (!movieDetailsState.error.isNullOrEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Error:\n${movieDetailsState.error}",
                textAlign = TextAlign.Center
            )
        } else {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    DetailsV2AppBar(
                        modifier = Modifier.fillMaxWidth(),
                        scrollBehavior = scrollBehavior,
                        movieDetails = movieDetailsState.movieDetails,
                        onNavigationIconClick = { navigator.goBack() },
                        onShareIconClick = {},
                        onFavoriteIconClick = {}
                    )
                }) {
                Column(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .verticalScroll(state = scrollState),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    //region Movie Ratings
                    val voteAverage = movieDetailsState.movieDetails?.voteAverage
                    voteAverage?.let {
                        MovieRatingSection(
                            popularity = voteAverage.getPopularity(),
                            voteAverage = voteAverage.getRating()
                        )
                    }
                    //endregion

                    //region Movie Overview
                    movieDetailsState.movieDetails?.overview?.let {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "Overview",
                            style = MaterialTheme.typography.h6,
                            fontSize = 20.sp,
                            color = MaterialTheme.colors.onSurface,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            text = it,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    //endregion

                    //region Movie Cast
                    movieDetailsState.movieCast?.let {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "Cast",
                            style = MaterialTheme.typography.h6,
                            fontSize = 20.sp
                        )

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(items = it) { item ->
                                ItemMovieCast(modifier = Modifier, actor = item)
                            }
                        }
                    }
                    //endregion

                    //region Similar Movies
                    movieDetailsState.similarMovies?.let {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "Similar Movies",
                            style = MaterialTheme.typography.h6,
                            fontSize = 20.sp,
                            color = MaterialTheme.colors.onSurface
                        )

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(items = it) { movie ->
                                MovieCardPortrait(movie = movie, onItemClick = {})
                            }
                        }
                    }
                    //endregion
                }
            }
        }
    }
}

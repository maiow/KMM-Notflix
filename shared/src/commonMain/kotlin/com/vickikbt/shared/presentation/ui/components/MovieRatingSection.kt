package com.vickikbt.shared.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MovieRatingSection(popularity: String?, voteAverage: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //region Popularity
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier,
                text = if (popularity.isNullOrEmpty()) "N/A" else popularity,
                style = MaterialTheme.typography.h6,
                fontSize = 42.sp,
                color = MaterialTheme.colors.onSurface,
            )

            Text(
                text = "Popularity",
                style = MaterialTheme.typography.h6,
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface,
            )
        }
        //endregion

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp),
            color = MaterialTheme.colors.onSurface
        )

        //region Rating
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier,
                imageVector = Icons.Rounded.Star,
                contentDescription = "Rating"
            )

            Text(
                modifier = Modifier,
                text = if (voteAverage.isNullOrEmpty()) "N/A" else "$voteAverage/5.0",
                style = MaterialTheme.typography.h6,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onSurface,
            )
        }
        //endregion
    }
}

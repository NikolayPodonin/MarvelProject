package com.podonin.marvel_details.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.podonin.base_ui.components.ErrorFullScreen
import com.podonin.base_ui.components.LoaderFullScreen
import com.podonin.marvel_details.R
import com.podonin.marvel_details.domain.model.FullCharacter
import com.podonin.marvel_details.redux.DetailsLoaded
import com.podonin.marvel_details.redux.EmptyError
import com.podonin.marvel_details.redux.EmptyLoading
import com.podonin.marvel_details.redux.Reload
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.lang.Float.min

private val imageHeight = 384.dp

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun DetailsScreen(
    characterId: String
) {
    val viewModel: DetailsViewModel = getViewModel { parametersOf(characterId) }
    val reduxState by viewModel.state.observeAsState()

    when (val state = reduxState) {
        EmptyError -> {
            ErrorFullScreen { viewModel.dispatch(Reload) }
        }
        EmptyLoading, null -> {
            LoaderFullScreen()
        }
        is DetailsLoaded -> {
            CharactersDetails(state.character)
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
private fun CharactersDetails(character: FullCharacter) {
    Box(modifier = Modifier.fillMaxSize()) {

        val state = rememberLazyListState()

        ParallaxImage(
            imageUrl = character.avatarUrl,
            contentDescription = character.description,
            state = state)
        DetailsLazyColumn(character, state)
    }
}

@Composable
private fun ParallaxImage(
    imageUrl: String,
    contentDescription: String?,
    state: LazyListState
) {
    val offset = if (state.firstVisibleItemIndex == 0) {
        state.firstVisibleItemScrollOffset
    } else {
        LocalDensity.current.run { imageHeight.roundToPx() }
    }

    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                crossfade(true)
                placeholder(drawableResId = R.drawable.ic_no_image)
                error(drawableResId = R.drawable.ic_no_image)
            }
        ),
        contentDescription = contentDescription,
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight)
            .graphicsLayer {
                alpha = min(1f, 1 - (offset / 600f))
                translationY = -offset * 0.1f
            },
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun DetailsLazyColumn(
    character: FullCharacter,
    state: LazyListState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = imageHeight / 2, start = 16.dp, end = 16.dp),
        state = state
    ) {
        item {
            Text(
                text = character.name,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = imageHeight)
            )
        }

        if (character.description.isNotBlank()) {
            item {
                Text(
                    text = character.description,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        if (character.comics.isNotEmpty()) {
            item {
                Header(text = stringResource(id = R.string.details_comics))
            }
        }

        items(character.comics) { comics ->
            ListItem(text = comics)
        }

        if (character.stories.isNotEmpty()) {
            item {
                Header(text = stringResource(id = R.string.details_stories))
            }
        }

        items(character.stories) { stories ->
            ListItem(text = stories)
        }

        if (character.events.isNotEmpty()) {
            item {
                Header(text = stringResource(id = R.string.details_events))
            }
        }

        items(character.events) { event ->
            ListItem(text = event)
        }

        if (character.series.isNotEmpty()) {
            item {
                Header(text = stringResource(id = R.string.details_series))
            }
        }

        items(character.series) { series ->
            ListItem(text = series)
        }
    }
}

@Composable
fun Header(text: String) {
    Text(text = text, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
}

@Composable
fun ListItem(text: String) {
    Text(text = text, fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
}
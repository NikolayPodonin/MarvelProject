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
    characterId: String,
    onBackPressed: () -> Unit
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

private var index = 0

@ExperimentalCoroutinesApi
@Composable
private fun CharactersDetails(character: FullCharacter) {
    Box(modifier = Modifier.fillMaxSize()) {

        val state = rememberLazyListState()

        ParallaxImage(
            "https://image.shutterstock.com/z/stock-photo-fresh-citrus-seamles-fine-pattern-ripe-fruit-tangerines-trendy-sunlight-summer-pattern-made-with-1944594160.jpg",
            null,
            state)
        DetailsLazyColumn(character, state)
//        Title(character, scroll.value)
//        Image(snack.imageUrl, scroll.value)
//        BackButton(upPress)
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
                placeholder(drawableResId = R.drawable.avatar_placeholder)
                error(drawableResId = R.drawable.avatar_placeholder)
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
            Header(
                text = stringResource(id = R.string.details_comics),
                modifier = Modifier.padding(top = imageHeight)
            )
        }

        items(character.comics) { comics ->
            Text(text = comics)
        }

        item {
            Header(
                text = stringResource(id = R.string.details_stories),
                Modifier.padding(top = 8.dp)
            )
        }

        items(character.stories) { stories ->
            Text(text = stories)
        }

        item {
            Header(
                text = stringResource(id = R.string.details_events),
                Modifier.padding(top = 8.dp)
            )
        }

        items(character.events) { event ->
            Text(text = event)
        }

        item {
            Header(
                text = stringResource(id = R.string.details_series),
                Modifier.padding(top = 8.dp)
            )
        }

        items(character.series) { series ->
            Text(text = series)
        }
    }
}

@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    Text(text = text, fontSize = 18.sp, modifier = modifier)
}
//
//@Composable
//fun CharacterItem(
//    id: Int,
//    name: String,
//    description: String,
//    avatarUrl: String,
//    onClick: (id: Int) -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .clickable { onClick.invoke(id) }
//    ) {
//        SnackImage(
//            imageUrl = avatarUrl,
//            contentDescription = "$name avatar",
//            modifier = Modifier.size(48.dp)
//        )
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 8.dp)
//        ) {
//            Text(text = name, fontSize = 16.sp, color = Color.Black)
//            Text(text = description, fontSize = 14.sp, color = Color.Gray)
//        }
//    }
//}
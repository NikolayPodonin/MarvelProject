package com.podonin.marvelcharacters.ui

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.podonin.base_ui.components.ErrorFullScreen
import com.podonin.base_ui.components.LoaderFullScreen
import com.podonin.marvelcharacters.R
import com.podonin.marvelcharacters.redux.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun CharactersScreen(
    onDetailsNavigate: (characterId: String) -> Unit
) {
    val viewModel: CharactersViewModel = getViewModel()
    val reduxState by viewModel.state.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.characters_title))
                }
            )
        }
    ) {
        when (val state = reduxState) {
            is WithCharacters -> {
                CharactersLazyList(
                    viewModel,
                    state,
                    onDetailsNavigate
                )
            }
            EmptyError -> {
                ErrorFullScreen { viewModel.dispatch(Reload) }
            }
            EmptyLoading, null -> {
                LoaderFullScreen()
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
private fun CharactersLazyList(
    viewModel: CharactersViewModel,
    state: WithCharacters,
    onDetailsNavigate: (characterId: String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(state.characters) { index, item ->
                CharacterItem(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    avatarUrl = item.avatarUrl
                ) { id ->
                    onDetailsNavigate.invoke(id)
                }
                if (state.characters.lastIndex == index) {
                    viewModel.dispatch(LoadNextPage)
                }
            }
        }
        NewCharactersLoader(
            state = state,
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun NewCharactersLoader(
    state: WithCharacters,
    modifier: Modifier
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = state is NewPageLoading,
        enter = slideInVertically(
            // Slide in from 40 dp from the top.
            initialOffsetY = { with(density) { 40.dp.roundToPx() } }
        ) + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Bottom
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically(
            targetOffsetY = { with(density) { 40.dp.roundToPx() } }
        ) + shrinkVertically() + fadeOut(),
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CharacterItem(
    id: String,
    name: String,
    description: String,
    avatarUrl: String,
    onClick: (id: String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick.invoke(id) }
    ) {
        val imagePainter = rememberImagePainter(
            data = avatarUrl,
            builder = {
                crossfade(true)
                placeholder(drawableResId = R.drawable.ic_no_image)
                error(drawableResId = R.drawable.ic_no_image)
            }
        )
        CharacterImage(
            imagePainter = imagePainter,
            contentDescription = "$name avatar",
            modifier = Modifier.size(48.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
                .align(CenterVertically)
        ) {
            Text(text = name, fontSize = 16.sp, color = Color.Black)
            if (description.isNotBlank()) {
                Text(text = description, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun CharacterImage(
    imagePainter: ImagePainter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    Surface(
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}
package com.podonin.marvelcharacters.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.podonin.marvelcharacters.domain.model.MarvelCharacter
import com.podonin.marvelcharacters.R
import com.podonin.marvelcharacters.redux.CharactersLoaded
import com.podonin.marvelcharacters.redux.EmptyLoading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.getViewModel

@ExperimentalCoroutinesApi
@Composable
fun CharactersScreen() {
    val viewModel: CharactersViewModel = getViewModel()
    val state by viewModel.state.observeAsState()

    when(state) {
        is CharactersLoaded -> {
            CharactersLazyList((state as CharactersLoaded).characters)
        }
        EmptyLoading, null -> {
            CharactersLoader()
        }
//        EmptyError -> //TODO()
    }
}

@Composable
fun CharactersLoader() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun CharactersLazyList(characters: List<MarvelCharacter>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(characters) { item: MarvelCharacter ->
            CharacterItem(
                name = item.name,
                description = item.description,
                avatarUrl = item.avatarUrl
            )
        }
    }
}

@Composable
fun CharacterItem(
    name: String,
    description: String,
    avatarUrl: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        SnackImage(
            imageUrl = avatarUrl,
            contentDescription = "$name avatar",
            modifier = Modifier.size(48.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        ) {
            Text(text = name, fontSize = 16.sp, color = Color.Black)
            Text(text = description, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun SnackImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    Surface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    crossfade(true)
                    placeholder(drawableResId = R.drawable.avatar_placeholder)
                }
            ),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}

@Preview("default")
@Composable
fun SnackCardPreview() {
    CharactersScreen()
}
package com.podonin.base_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.podonin.base_ui.R

@Composable
fun LoaderFullScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ErrorFullScreen(onReload: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = onReload,
            modifier = Modifier.align(Alignment.Center)
        ) {
            val reloadStr = stringResource(id = R.string.reload)
            Icon(
                painterResource(id = R.drawable.ic_refresh),
                reloadStr
            )
            Text(text = reloadStr)
        }
    }
}
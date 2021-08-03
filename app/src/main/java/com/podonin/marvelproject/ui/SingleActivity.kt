package com.podonin.marvelproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.google.accompanist.insets.ProvideWindowInsets
import com.podonin.base_ui.theme.MarvelProjectTheme
import com.podonin.marvelproject.navigation.MarvelNavGraph
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SingleActivity : ComponentActivity() {
    @ExperimentalCoroutinesApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProvideWindowInsets {
                MarvelProjectTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        MarvelNavGraph()
                    }
                }
            }
        }
    }
}
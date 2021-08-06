package com.podonin.marvelproject.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.podonin.marvel_details.ui.DetailsScreen
import com.podonin.marvelcharacters.ui.CharactersScreen
import com.podonin.marvelproject.navigation.MainDestinations.CharacterDetails
import com.podonin.marvelproject.navigation.MainDestinations.CharactersList
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 * Destinations used in the ([MarvelNavGraph]).
 * I don't like this navigation system, but for this project it's
 * more easy then create my own system.
 */
sealed class MainDestinations(val destination: String) {
    object CharactersList : MainDestinations("CharactersList")
    object CharacterDetails : MainDestinations("CharacterDetails") {
        const val characterId = "characterId"
    }
}

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun MarvelNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = CharactersList.destination,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            CharactersList.destination
        ) { from ->
            CharactersScreen { characterId ->
                // In order to discard duplicated navigation events, we check the Lifecycle
                if (from.lifecycleIsResumed()) {
                    navController.navigate("${CharacterDetails.destination}/$characterId")
                }
            }
        }
        composable(
            "${CharacterDetails.destination}/{${CharacterDetails.characterId}}",
            arguments = listOf(navArgument(CharacterDetails.characterId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val characterId = arguments.getString(CharacterDetails.characterId)
            DetailsScreen(
                characterId = characterId.orEmpty()
            )
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

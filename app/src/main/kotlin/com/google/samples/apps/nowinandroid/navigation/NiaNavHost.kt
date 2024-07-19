/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.createGraph
import com.google.samples.apps.nowinandroid.feature.bookmarks.navigation.bookmarksScreen
import com.google.samples.apps.nowinandroid.feature.foryou.navigation.FOR_YOU_ROUTE
import com.google.samples.apps.nowinandroid.feature.foryou.navigation.forYouScreen
import com.google.samples.apps.nowinandroid.feature.search.navigation.searchScreen
import com.google.samples.apps.nowinandroid.navigation.TopLevelDestination.INTERESTS
import com.google.samples.apps.nowinandroid.ui.NiaAppState
import com.google.samples.apps.nowinandroid.ui.interests2pane.Interest
import com.google.samples.apps.nowinandroid.ui.interests2pane.interestsListDetailScreen
import com.google.samples.apps.nowinandroid.ui.interests2pane.navigateToInterests

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun NiaNavHost(
    appState: NiaAppState,
    onShowSnackBar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = FOR_YOU_ROUTE,
//    niaNavHostViewModel: NiaNavHostViewModel = hiltViewModel(),
//    onTopicIdPassed: (String?) -> Unit = {},
) {
    val navController = appState.navController
//    val selectedTopic by niaNavHostViewModel.selectedTopicId.collectAsStateWithLifecycle()
    val navGraph: NavGraph = remember {
        navController.createGraph(
            startDestination = startDestination,
            route = "ROOT",
        ) {
            forYouScreen(
                onTopicClick = {
//                    niaNavHostViewModel.onTopicClick(it)
//                    navController.navigateToInterests(selectedTopic, appState.topLevelNavOptions(false))
                    navController.navigateToInterests(Interest(it), appState.topLevelNavOptions(false))
                },
            )
            bookmarksScreen(
                onTopicClick = {
//                    navController.navigateToInterests(selectedTopic, appState.topLevelNavOptions(false))
                    navController.navigateToInterests(Interest(it), appState.topLevelNavOptions(false))
                },
                onShowSnackBar = onShowSnackBar,
            )
            searchScreen(
                onBackClick = navController::popBackStack,
                onInterestsClick = { appState.navigateToTopLevelDestination(INTERESTS) },
//                onTopicClick = { navController.navigateToInterests(selectedTopic, appState.topLevelNavOptions(false)) },
                onTopicClick = { navController.navigateToInterests(Interest(it), appState.topLevelNavOptions(false)) },
            )
            interestsListDetailScreen()
//            (
//                onTopicIdPassed = onqTopicIdPassed,
//            )
        }
    }
//    navGraph.setStartDestination(Interest())
    NavHost(
        navController = navController,
        graph = navGraph,
        modifier = modifier,
    )
//    NavHost(
//        navController = navController,
//        startDestination = startDestination,
//        modifier = modifier,
//    ) {
//        forYouScreen(onTopicClick = { navController.navigateToInterests(it, appState.topLevelNavOptions(false)) })
//        bookmarksScreen(
//            onTopicClick = { navController.navigateToInterests(it, appState.topLevelNavOptions(false)) },
//            onShowSnackBar = onShowSnackBar,
//        )
//        searchScreen(
//            onBackClick = navController::popBackStack,
//            onInterestsClick = { appState.navigateToTopLevelDestination(INTERESTS) },
//            onTopicClick = { navController.navigateToInterests(it, appState.topLevelNavOptions(false)) },
//        )
//        interestsListDetailScreen()
//    }
}

/*
 * Copyright 2024 The Android Open Source Project
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

package com.google.samples.apps.nowinandroid.ui.interests2pane

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.samples.apps.nowinandroid.feature.interests.InterestsRoute
import com.google.samples.apps.nowinandroid.feature.interests.navigation.TOPIC_ID_ARG
import com.google.samples.apps.nowinandroid.feature.topic.TopicDetailPlaceholder
import com.google.samples.apps.nowinandroid.feature.topic.navigation.TOPIC_ROUTE
import com.google.samples.apps.nowinandroid.feature.topic.navigation.createTopicRoute
import com.google.samples.apps.nowinandroid.feature.topic.navigation.navigateToTopic
import com.google.samples.apps.nowinandroid.feature.topic.navigation.topicScreen
import kotlinx.serialization.Serializable
import java.util.UUID

private const val DETAIL_PANE_NAVHOST_ROUTE = "detail_pane_route"

val INTERESTS_ROUTE = "${Interest::class.qualifiedName}?$TOPIC_ID_ARG={$TOPIC_ID_ARG}"

@Serializable
data class Interest(
    val topicId: String? = null,
)

// fun NavController.navigateToInterests(topicId: String? = null, navOptions: NavOptions? = null) {
fun NavController.navigateToInterests(interest: Interest = Interest(), navOptions: NavOptions? = null) {
//    val route = interest.topicId?.let { topicId ->
//        Log.d("Rodrigo", "route: navigateToInterests: $INTERESTS_ROUTE_BASE?$TOPIC_ID_ARG=$topicId")
//        "$INTERESTS_ROUTE_BASE?$TOPIC_ID_ARG=$topicId"
//    } ?: INTERESTS_ROUTE_BASE
    navigate(interest, navOptions)
//    navigate(interest)
}

fun NavGraphBuilder.interestsListDetailScreen(
//    onTopicIdPassed: (String?) -> Unit = {},
) {
    composable<Interest> { backStackEntry ->
        val interest: Interest = backStackEntry.toRoute()
        Log.e("Rodrigo", "route: navigateToInterests: interest: ${interest.topicId}")
//        val selectedTopicId: String? = backStackEntry.arguments?.getString(TOPIC_ID_ARG)
//        Log.e("Rodrigo", "route: navigateToInterests: selectedTopicId: $selectedTopicId")
//        onTopicIdPassed(selectedTopicId)
        InterestsListDetailScreen(
            selectedTopicId = interest.topicId,
//            onTopicIdPassed = onTopicIdPassed,
        )
    }

//    composable(
//        route = INTERESTS_ROUTE,
//        arguments = listOf(
//            navArgument(TOPIC_ID_ARG) {
//                type = NavType.StringType
//                defaultValue = null
//                nullable = true
//            },
//        ),
//    ) { backStackEntry ->
//        val selectedTopicId: String? = backStackEntry.arguments?.getString(TOPIC_ID_ARG)
//        Log.e("Rodrigo", "route: navigateToInterests: selectedTopicId: $selectedTopicId")
// //        onTopicIdPassed(selectedTopicId)
//        InterestsListDetailScreen(
// //            selectedTopicId = selectedTopicId,
// //            onTopicIdPassed = onTopicIdPassed,
//        )
//    }
}

@Composable
internal fun InterestsListDetailScreen(
    selectedTopicId: String? = null,
    viewModel: Interests2PaneViewModel = hiltViewModel(),
//    onTopicIdPassed: (String?) -> Unit = {},

) {
    selectedTopicId?.let {
        viewModel.onTopicClick(selectedTopicId)
    }
    val selectedTopicId by viewModel.selectedTopicId.collectAsStateWithLifecycle()
    Log.d(
        "Rodrigo",
        "route: navigateToInterests: InterestsListDetailScreen: selectedTopicId: $selectedTopicId",
    )
    InterestsListDetailScreen(
        selectedTopicId = selectedTopicId,
        onTopicClick = viewModel::onTopicClick,
//        { selectedTopicId ->
//            onTopicIdPassed(selectedTopicId)
//            viewModel.onTopicClick(selectedTopicId)
//        },
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun InterestsListDetailScreen(
    selectedTopicId: String?,
    onTopicClick: (String) -> Unit,
) {
    Log.d("Rodrigo", "route: navigateToInterests: InterestsListDetailScreen: InterestsListDetailScreen: selectedTopicId: $selectedTopicId")
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator(
        initialDestinationHistory = listOfNotNull(
            ThreePaneScaffoldDestinationItem(ListDetailPaneScaffoldRole.List),
            ThreePaneScaffoldDestinationItem<Nothing>(ListDetailPaneScaffoldRole.Detail).takeIf {
                selectedTopicId != null
            },
        ),
    )
    BackHandler(listDetailNavigator.canNavigateBack()) {
        listDetailNavigator.navigateBack()
    }

    var nestedNavHostStartDestination by remember {
        mutableStateOf(selectedTopicId?.let(::createTopicRoute) ?: TOPIC_ROUTE)
    }
    var nestedNavKey by rememberSaveable(
        stateSaver = Saver({ it.toString() }, UUID::fromString),
    ) {
        mutableStateOf(UUID.randomUUID())
    }
    val nestedNavController = key(nestedNavKey) {
        rememberNavController()
    }

    fun onTopicClickShowDetailPane(topicId: String) {
        onTopicClick(topicId)
        if (listDetailNavigator.isDetailPaneVisible()) {
            // If the detail pane was visible, then use the nestedNavController navigate call
            // directly
            nestedNavController.navigateToTopic(topicId) {
                popUpTo(DETAIL_PANE_NAVHOST_ROUTE)
            }
        } else {
            // Otherwise, recreate the NavHost entirely, and start at the new destination
            Log.d("Rodrigo", "route: navigateToInterests: InterestsListDetailScreen: InterestsListDetailScreen: createTopicRoute(topicId): ${createTopicRoute(topicId)}")
            nestedNavHostStartDestination = createTopicRoute(topicId)
            nestedNavKey = UUID.randomUUID()
        }
        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
    }

    ListDetailPaneScaffold(
        value = listDetailNavigator.scaffoldValue,
        directive = listDetailNavigator.scaffoldDirective,
        listPane = {
            AnimatedPane {
                InterestsRoute(
                    onTopicClick = ::onTopicClickShowDetailPane,
                    highlightSelectedTopic = listDetailNavigator.isDetailPaneVisible(),
                )
            }
        },
        detailPane = {
            AnimatedPane {
                key(nestedNavKey) {
                    NavHost(
                        navController = nestedNavController,
                        startDestination = nestedNavHostStartDestination,
                        route = DETAIL_PANE_NAVHOST_ROUTE,
                    ) {
                        topicScreen(
                            showBackButton = !listDetailNavigator.isListPaneVisible(),
                            onBackClick = listDetailNavigator::navigateBack,
                            onTopicClick = ::onTopicClickShowDetailPane,
                        )
                        composable(route = TOPIC_ROUTE) {
                            TopicDetailPlaceholder()
                        }
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isListPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isDetailPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded

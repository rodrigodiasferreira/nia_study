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

package com.google.samples.apps.nowinandroid.feature.interests.navigation

const val TOPIC_ID_ARG = "topicId"
const val INTERESTS_ROUTE_BASE = "interests_route"
// const val INTERESTS_ROUTE = "$INTERESTS_ROUTE_BASE?$TOPIC_ID_ARG={$TOPIC_ID_ARG}"

// fun NavController.navigateToInterests(topicId: String? = null, navOptions: NavOptions? = null) {
//     val route = topicId?.let {
//         "${INTERESTS_ROUTE_BASE}?${TOPIC_ID_ARG}=$topicId"
//     } ?: INTERESTS_ROUTE_BASE
//     navigate(route, navOptions)
// }

// fun NavGraphBuilder.interestsScreen(
//    onTopicClick: (String) -> Unit,
// ) {
//    composable(
//        route = INTERESTS_ROUTE,
//        arguments = listOf(
//            navArgument(TOPIC_ID_ARG) {
//                defaultValue = null
//                nullable = true
//                type = NavType.StringType
//            },
//        ),
//    ) {
//        InterestsRoute(onTopicClick = onTopicClick)
//    }
// }

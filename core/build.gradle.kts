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

plugins {
    id("base")
}

tasks.register("testDemoDebugUnitTest") {
    dependsOn(
        "analytics:testDemoDebugUnitTest",
        "android-os-utils:testDemoDebugUnitTest",
        "common:testDemoDebugUnitTest",
        "data:testDemoDebugUnitTest",
        "data-test:testDemoDebugUnitTest",
        "database:testDemoDebugUnitTest",
        "datastore:testDemoDebugUnitTest",
        "datastore-proto:testDemoDebugUnitTest",
        "datastore-test:testDemoDebugUnitTest",
        "designsystem:testDemoDebugUnitTest",
        "domain:testDemoDebugUnitTest",
        "network:testDemoDebugUnitTest",
        "notifications:testDemoDebugUnitTest",
        "screenshot-testing:testDemoDebugUnitTest",
        "testing:testDemoDebugUnitTest",
        "ui:testDemoDebugUnitTest",
    )
}

tasks.register("connectedDemoDebugAndroidTest") {
    dependsOn(
        "database:connectedDemoDebugAndroidTest",
        "ui:connectedDemoDebugAndroidTest",
    )
}

tasks.register("executeUnitAndInstrumentedTests") {
    dependsOn(
        "testDemoDebugUnitTest",
        "connectedDemoDebugAndroidTest",
    )
}

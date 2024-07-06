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

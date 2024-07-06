plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.library.jacoco)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.google.samples.apps.nowinandroid.core.android_os_utils"
}
dependencies {
    implementation(libs.androidx.annotation.jvm)
}

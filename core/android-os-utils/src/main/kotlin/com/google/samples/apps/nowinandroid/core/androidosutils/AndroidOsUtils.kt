package com.google.samples.apps.nowinandroid.core.androidosutils

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

object AndroidOsUtils {

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    fun isSosOnwards() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

}

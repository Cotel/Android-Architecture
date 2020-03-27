object GradlePluginVersion {
    const val ANDROID_GRADLE = "3.5.3"
    const val KTLINT_GRADLE = "9.1.1"
    const val DETEKT = "1.4.0"
    const val GRADLE_VERSION_PLUGIN = "0.22.0"
    const val KOTLIN = CoreVersion.KOTLIN
}

object GradlePluginId {
    const val DETEKT = "io.gitlab.arturbosch.detekt"
    const val KTLINT_GRADLE = "org.jlleitschuh.gradle.ktlint"
    const val ANDROID_APPLICATION = "com.android.application"
    const val ANDROID_DYNAMIC_FEATURE = "com.android.dynamic-feature"
    const val ANDROID_LIBRARY = "com.android.library"
    const val KOTLIN_JVM = "org.jetbrains.kotlin.jvm"
    const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    const val KOTLIN_ANDROID_EXTENSIONS =
        "org.jetbrains.kotlin.android.extensions"
    const val KOTLIN_ANNOTATION_PROCESSOR = "kapt"
    const val GRADLE_VERSION_PLUGIN = "com.github.ben-manes.versions"
}

object GradleOldWayPlugins {
    const val ANDROID_GRADLE =
        "com.android.tools.build:gradle:${GradlePluginVersion.ANDROID_GRADLE}"
}

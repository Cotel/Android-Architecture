private object TestLibraryVersion {
    const val JUNIT = "4.13"
    const val TEST_RUNNER = "1.0.2"
    const val ESPRESSO_CORE = "3.0.2"
    const val ANDROID_X_TEST = "2.1.0"
}

object TestLibraryDependency {
    const val JUNIT = "junit:junit:${TestLibraryVersion.JUNIT}"
    const val TEST_RUNNER =
        "com.android.support.test:runner:${TestLibraryVersion.TEST_RUNNER}"
    const val ESPRESSO_CORE =
        "com.android.support.test.espresso:espresso-core:${TestLibraryVersion.ESPRESSO_CORE}"
    const val COROUTINES_TEST =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${CoreVersion.COROUTINES_ANDROID}"
    const val ANDROID_X_CORE_TESTING =
        "android.arch.core:core-testing:${TestLibraryVersion.ANDROID_X_TEST}"

    const val KOIN_TEST = "org.koin:koin-test:${LibraryVersion.KOIN}"
}

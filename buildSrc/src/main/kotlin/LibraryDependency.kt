@file:Suppress("detekt.StringLiteralDuplication")

internal object LibraryVersion {
    const val RETROFIT = "2.7.1"
    const val OK_HTTP = "4.3.1"

    const val PLAY_CORE = "1.6.4"
    const val APP_COMPACT = "1.1.0"
    const val RECYCLER_VIEW = "1.1.0"
    const val COORDINATOR_LAYOUT = "1.1.0"
    // 1.1.x version is required in order to support the dark theme functionality in
    // Android Q (adds Theme.MaterialComponents.DayNight)
    const val MATERIAL = "1.1.0-alpha09"
    const val CONSTRAINT_LAYOUT = "1.1.3"
    const val CORE_KTX = "1.1.0"
    const val FRAGMENT_KTX = "1.1.0"
    const val LIFECYCLE = "1.1.1"
    const val LIFECYCLE_VIEW_MODEL_KTX = "2.2.0"

    const val ROOM = "2.2.3"

    const val COIL = "0.9.1"

    const val DAGGER = "2.28.1"

    const val ARROW = "0.10.4"
}

object LibraryDependency {
    const val KOTLIN =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${CoreVersion.KOTLIN}"

    // Required by Android dynamic feature modules and SafeArgs
    const val KOTLIN_REFLECT =
        "org.jetbrains.kotlin:kotlin-reflect:${CoreVersion.KOTLIN}"
    const val RETROFIT =
        "com.squareup.retrofit2:retrofit:${LibraryVersion.RETROFIT}"
    const val RETROFIT_MOSHI_CONVERTER =
        "com.squareup.retrofit2:converter-moshi:${LibraryVersion.RETROFIT}"

    const val DAGGER =  "com.google.dagger:dagger:${LibraryVersion.DAGGER}"
    const val DAGGER_KAPT = "com.google.dagger:dagger-compiler:${LibraryVersion.DAGGER}"

    // Retrofit will use okhttp 4 (it tas binary capability with okhttp 3)
    // See: https://square.github.io/okhttp/upgrading_to_okhttp_4/
    const val OK_HTTP = "com.squareup.okhttp3:okhttp:${LibraryVersion.OK_HTTP}"
    const val LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${LibraryVersion.OK_HTTP}"
    const val SUPPORT_CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${LibraryVersion.CONSTRAINT_LAYOUT}"
    const val PLAY_CORE =
        "com.google.android.play:core:${LibraryVersion.PLAY_CORE}"
    const val APP_COMPACT =
        "androidx.appcompat:appcompat:${LibraryVersion.APP_COMPACT}"
    const val RECYCLER_VIEW =
        "androidx.recyclerview:recyclerview:${LibraryVersion.RECYCLER_VIEW}"
    const val COORDINATOR_LAYOUT =
        "androidx.coordinatorlayout:coordinatorlayout:${LibraryVersion.COORDINATOR_LAYOUT}"
    const val MATERIAL =
        "com.google.android.material:material:${LibraryVersion.MATERIAL}"
    const val COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${CoreVersion.COROUTINES_ANDROID}"
    const val CORE_KTX = "androidx.core:core-ktx:${LibraryVersion.CORE_KTX}"
    const val FRAGMENT_KTX =
        "androidx.fragment:fragment-ktx:${LibraryVersion.FRAGMENT_KTX}"
    const val LIFECYCLE_EXTENSIONS =
        "android.arch.lifecycle:extensions:${LibraryVersion.LIFECYCLE}"
    const val LIFECYCLE_VIEW_MODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersion.LIFECYCLE_VIEW_MODEL_KTX}"
    const val LIFECYCLE_RUNTIME_KTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${LibraryVersion.LIFECYCLE_VIEW_MODEL_KTX}"

    const val ROOM = "androidx.room:room-runtime:${LibraryVersion.ROOM}"
    const val ROOM_KAPT = "androidx.room:room-compiler:${LibraryVersion.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${LibraryVersion.ROOM}"

    const val COIL = "io.coil-kt:coil:${LibraryVersion.COIL}"

    const val ARROW_CORE = "io.arrow-kt:arrow-core:${LibraryVersion.ARROW}"
    const val ARROW_SYNTAX = "io.arrow-kt:arrow-syntax:${LibraryVersion.ARROW}"
    const val ARROW_FX = "io.arrow-kt:arrow-fx:${LibraryVersion.ARROW}"
}

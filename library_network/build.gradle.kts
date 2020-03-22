plugins {
    id(GradlePluginId.KOTLIN_JVM)
}

dependencies {
    api(LibraryDependency.RETROFIT)

    implementation(LibraryDependency.OK_HTTP)
    implementation(LibraryDependency.LOGGING_INTERCEPTOR)
    implementation(LibraryDependency.RETROFIT_MOSHI_CONVERTER)
}

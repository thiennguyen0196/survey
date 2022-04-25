import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.addAndroidUI(configurationName: String = "implementation") {
    add(configurationName, AndroidUI.core)
    add(configurationName, AndroidUI.appcompat)
    add(configurationName, AndroidUI.recycler_view)
    add(configurationName, AndroidUI.material)
    add(configurationName, AndroidUI.legacy_support_v4)
    add(configurationName, AndroidUI.vector_drawable)
    add(configurationName, AndroidUI.constraint_layout)
    add(configurationName, AndroidUI.swipe_refresh_layout)
    add(configurationName, AndroidUI.swipe_refresh_layout)
}

fun DependencyHandler.addLifeCycle(configurationName: String = "implementation") {
    add(configurationName, Lifecycle.common)
    add(configurationName, Lifecycle.livedata)
    add(configurationName, Lifecycle.runtime)
    add(configurationName, Lifecycle.savestate)
    add(configurationName, Lifecycle.viewmodel)
    add(configurationName, Lifecycle.viewmodel_compose)
}

fun DependencyHandler.addNavigation(configurationName: String = "implementation") {
    add(configurationName, Navigation.runtime)
    add(configurationName, Navigation.fragment)
    add(configurationName, Navigation.ui)
}

fun DependencyHandler.addRetrofit(configurationName: String = "implementation") {
    add(configurationName, Retrofit.retrofit)
    add(configurationName, Retrofit.retrofit_gson)
    add(configurationName, Retrofit.retrofit_adapter_rxjava3)
}

fun DependencyHandler.addShimmerLayout(configurationName: String = "implementation") {
    add(configurationName, ShimmerLayout.shimmer_layout)
}

fun DependencyHandler.addHilt(configurationName: String = "implementation") {
    add(configurationName, Hilt.android)
    add("kapt", Hilt.android_compiler)
}

fun DependencyHandler.addKotlin(configurationName: String = "implementation") {
    add(configurationName, Kotlin.stdlib)
}

fun DependencyHandler.addTimber(configurationName: String = "implementation") {
    add(configurationName, Timber.timer)
}

fun DependencyHandler.addGlide(configurationName: String = "implementation") {
    add(configurationName, Glide.glide)
    add("kapt", Glide.glide_compiler)
}

fun DependencyHandler.addReactiveX(configurationName: String = "implementation") {
    add(configurationName, ReactiveX.rx_java_3)
    add(configurationName, ReactiveX.rx_android_3)
    add(configurationName, ReactiveX.rx_kotlin_3)
}

fun DependencyHandler.addOkHttp(configurationName: String = "implementation") {
    add(configurationName, OkHttp.okhttp)
    add(configurationName, OkHttp.logging_interceptor)
}

fun DependencyHandler.addJunit(configurationName: String = "testImplementation") {
    add(configurationName, Junit.junit)
}

fun DependencyHandler.addMockito(configurationName: String = "testImplementation") {
    add(configurationName, Mockito.core)
    add(configurationName, Mockito.inline)
    add(configurationName, Mockito.kotlin)
}

fun DependencyHandler.addMockWebServer(configurationName: String = "testImplementation") {
    add(configurationName, MockWebServer.mock_web_server)
}

fun DependencyHandler.addRobolectric(configurationName: String = "testImplementation") {
    add(configurationName, Robolectric.robolectric)
}

fun DependencyHandler.addGson(configurationName: String = "testImplementation") {
    add(configurationName, Gson.gson)
}
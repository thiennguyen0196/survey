plugins {
    id ("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = Configuration.compileSdk

    defaultConfig {
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
    }

    sourceSets["test"].resources {
        srcDir("src/test/resources")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

    lint {
        xmlOutput = file("build/reports/lint/lint-result.xml")
        xmlReport = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Internal modules
    implementation(project(":domain"))

    // Kotlin
    addKotlin()

    // Hilt
    addHilt()

    // Timber
    addTimber()

    // Rx
    addReactiveX()

    // Retrofit
    addRetrofit()

    // OkHttp
    addOkHttp()

    // Unit testing
    addJunit()
    addMockito()
    addRobolectric()
    addMockWebServer()
}
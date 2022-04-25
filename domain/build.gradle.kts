plugins {
    id("com.android.library")
    id("survey-plugin")
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

    // Kotlin
    addKotlin()

    // Hilt
    addHilt()

    // Timber
    addTimber()

    // Rx
    addReactiveX()

    // Unit testing
    addJunit()
    addMockito()
}
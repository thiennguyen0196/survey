plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("survey-plugin")
}

android {
    compileSdk = Configuration.compileSdk

    defaultConfig {
        applicationId = "com.thiennguyen.survey"
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            isTestCoverageEnabled = true
        }
    }

    flavorDimensions.add("app")
    productFlavors {
        create("staging") {
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            resValue("string", "app_name", "Survey Staging")
        }
        create("prod") {
            resValue("string", "app_name", "Survey")
        }
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

    buildFeatures {
        viewBinding = true
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
    implementation(project(":data"))
    implementation(project(":domain"))

    // Kotlin
    addKotlin()

    // UI
    addAndroidUI()
    addShimmerLayout()
    addGlide()

    // Navigation
    addNavigation()

    // Lifecycle
    addLifeCycle()

    // Hilt
    addHilt()

    // Timber
    addTimber()

    // Rx
    addReactiveX()

    // Unit testing
    addJunit()
    addMockito()
    addRobolectric()
    addAndroidArchCoreTesting()
}
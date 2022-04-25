import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("survey-plugin") {
            id = "survey-plugin"
            implementationClass = "SurveyPlugin"
        }
    }
}

repositories {
    mavenCentral()
}

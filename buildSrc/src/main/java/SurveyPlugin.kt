import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register

class SurveyPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            setupPlugin()
            setupKtlint()
        }
    }

    private fun Project.setupPlugin() {
        plugins.run {
            apply("kotlin-android")
            apply("kotlin-kapt")
            apply("kotlin-parcelize")
        }
    }

    private fun Project.setupKtlint() {
        val ktlint = configurations.create("ktlint")
        dependencies {
            add("ktlint", Ktlint.ktlint)
        }
        tasks.register<JavaExec>("ktlintCheck") {
            group = "verification"
            description = "Check Kotlin code style."
            classpath = ktlint
            mainClass.set("com.pinterest.ktlint.Main")
            args("--android", "src/**/*.kt")
        }

        tasks.register<JavaExec>("ktlintFormat") {
            group = "formatting"
            description = "Fix Kotlin code style deviations."
            classpath = ktlint
            mainClass.set("com.pinterest.ktlint.Main")
            args("--android", "-F", "src/**/*.kt")
        }
    }
}
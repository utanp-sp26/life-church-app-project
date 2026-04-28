import org.gradle.api.GradleException
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use(::load)
    }
}

fun localSecretOrNull(key: String): String? =
    localProperties.getProperty(key)?.trim()?.takeIf { it.isNotEmpty() }

fun buildConfigString(value: String): String =
    "\"${value.replace("\\", "\\\\").replace("\"", "\\\"")}\""

android {
    namespace = "edu.utap.life_church_app"
    compileSdk = 36

    defaultConfig {
        applicationId = "edu.utap.life_church_app"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            val debugBackendUrl = localSecretOrNull("GIVING_BACKEND_URL_DEBUG") ?: ""
            val debugStripeKey = localSecretOrNull("STRIPE_PUBLISHABLE_KEY_DEBUG") ?: ""
            buildConfigField("String", "GIVING_BACKEND_URL", buildConfigString(debugBackendUrl))
            buildConfigField("String", "STRIPE_PUBLISHABLE_KEY", buildConfigString(debugStripeKey))
            buildConfigField("boolean", "ALLOW_MOCK_GOOGLE_PAY", "true")
        }
        release {
            val releaseBackendUrl = localSecretOrNull("GIVING_BACKEND_URL_RELEASE")
                ?: throw GradleException("Missing GIVING_BACKEND_URL_RELEASE in local.properties")
            val releaseStripeKey = localSecretOrNull("STRIPE_PUBLISHABLE_KEY_RELEASE")
                ?: throw GradleException("Missing STRIPE_PUBLISHABLE_KEY_RELEASE in local.properties")
            buildConfigField("String", "GIVING_BACKEND_URL", buildConfigString(releaseBackendUrl))
            buildConfigField("String", "STRIPE_PUBLISHABLE_KEY", buildConfigString(releaseStripeKey))
            buildConfigField("boolean", "ALLOW_MOCK_GOOGLE_PAY", "false")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

//    kotlinOptions {
//        jvmTarget = "11"
//    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation("androidx.compose.material:material-icons-extended")
    implementation(libs.play.services.wallet)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.analytics)

    implementation("io.coil-kt:coil-compose:2.6.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

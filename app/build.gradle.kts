plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.appapoyoemocional"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.appapoyoemocional"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    android {
        // ... tu configuración actual (namespace, compileSdk, etc.)

        buildFeatures {
            compose = true
        }

        packaging {
            resources {
                excludes += "META-INF/LICENSE.md"
                excludes += "META-INF/AL2.0"
                excludes += "META-INF/LGPL2.1"
                excludes += "META-INF/LICENSE-notice.md"
            }
        }

    }
}

dependencies {
    // jetpack compose y material 3
    implementation("androidx.activity:activity-compose:1.12.0")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    //retrofit y gson coverter
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    //corrutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    implementation("io.coil-kt:coil-compose:2.6.0")//

    implementation("androidx.compose.material:material-icons-extended:1.6.0")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation(libs.androidx.ui.test.junit4)

    //kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    //junit5
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("junit:junit:4.13.2")//
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    //mockk
    testImplementation("io.mockk:mockk:1.13.10")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    //compose ui test

    androidTestImplementation("androidx.navigation:navigation-testing:2.7.5")

// MockK para Android Tests (para mockear NavController y ViewModel)
    androidTestImplementation("io.mockk:mockk-android:1.13.10")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.2")

    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.2")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.vision.common)
    implementation(libs.play.services.mlkit.face.detection)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
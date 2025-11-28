plugins {
    // 1. Plugins (Van siempre al inicio)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

// 2. Bloque principal de Android
android {

    // --- CONFIGURACIÓN BÁSICA ---
    namespace = "com.example.appapoyoemocional"

    // Asumo que 'release(36)' es una función que resuelve la versión 36
    compileSdk = 36 // Usar un número entero si no se usa release()
    // compileSdk { version = 34 } // Si estás usando el nuevo DSL para compileSdk

    defaultConfig {
        applicationId = "com.example.appapoyoemocional"
        minSdk = 33
        targetSdk = 36 // Target debe ser consistente
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // --- MANEJO DE DEPENDENCIAS Y RESOLUCIÓN (IMPORTANTE) ---
    // Este bloque DEBE ir aquí dentro de 'android' para influir en las dependencias.
    configurations.all {
        resolutionStrategy {
            // Forzamos la resolución para prevenir conflictos de versiones en AndroidX Test
            // NOTA: No se debe forzar 1.4.0 Y 1.1.5 a la vez. Usaremos 1.4.0 como la más alta viable.

            // 1. Forzar Runner:
            force("androidx.test:runner:1.5.0")

            // 2. Forzar Ext-JUnit: Usamos 1.4.0 como la más alta. (La última línea duplicada de 1.1.5 fue eliminada/reemplazada)
            force("androidx.test.ext:junit:1.4.0")

            // 3. Forzar Espresso Core:
            force("androidx.test.espresso:espresso-core:3.5.1")

            force("androidx.test.ext:junit:1.1.5")
        }
    }

    // --- OTRAS CONFIGURACIONES DE COMPILACIÓN ---
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    // --- CONFIGURACIÓN DE PRUEBAS ---
    testOptions {
        unitTests.isIncludeAndroidResources = true // Para tests unitarios que acceden a recursos (Robolectric)
    }

    // --- CONFIGURACIÓN DE FEATURES ---
    buildFeatures {
        compose = true // Solo se necesita declarar una vez
    }

    // --- CONFIGURACIÓN DE EMPAQUETADO ---
    packaging {
        resources {
            // Exclusiones para resolver el fallo de 'mergeDebugAndroidTestJavaResource'
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

    // --- BUILD TYPES ---
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")


    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.2")


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

    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")



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


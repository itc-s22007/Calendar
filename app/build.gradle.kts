plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    kotlin("plugin.serialization")
}

android {
    namespace = "jp.ac.it_college.std.s22007.tecnos_assigment"
    compileSdk = 34

    defaultConfig {
        applicationId = "jp.ac.it_college.std.s22007.tecnos_assigment"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //Calendar
//    implementation("com.kizitonwose.calendar:compose:2.2.0")
    implementation("com.kizitonwose.calendar:compose:2.0.4")
    implementation("com.google.android.material:material:1.11.0")

    val composeBom = platform("androidx.compose:compose-bom")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Material 3
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")

    //preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-graphics")

    //Default
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")


    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.2.0")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.2.0")

//    implementation(libs.androidx.compose.material3)

    // fireBase
    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))
    implementation("com.google.firebase:firebase-analytics")

    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database:20.0.3")

    implementation(platform("com.google.firebase:firebase-bom:32.4.1"))
    implementation("com.google.firebase:firebase-firestore-ktx")

    //祝日API
    implementation("com.google.code.gson:gson:2.8.6")
    //OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.9.0")


    implementation ("com.google.accompanist:accompanist-pager:0.24.7-alpha")
}

//dependencies {
//    implementation("androidx.core:core-ktx:1.12.0")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
//    implementation("androidx.datastore:datastore-preferences:1.1.0-alpha06")
//    implementation("com.google.firebase:firebase-database-ktx:20.3.0")
//    implementation("com.google.android.gms:play-services-location:21.1.0")
//    implementation("com.google.firebase:firebase-firestore-ktx:24.10.3")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//
//    // Compose
//    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
//    implementation(composeBom)
//    implementation("androidx.activity:activity-compose:1.8.0")
//    implementation("androidx.compose.material3:material3")
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    androidTestImplementation(composeBom)
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//
//    // Navigation
//    implementation("androidx.navigation:navigation-compose:2.7.5")
//
//    // Ktor
//    implementation("io.ktor:ktor-client-core:2.3.6")
//    implementation("io.ktor:ktor-client-cio:2.3.6")
//    implementation("io.ktor:ktor-client-content-negotiation:2.3.6")
//    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")
//
//    // Room
//    implementation("androidx.room:room-runtime:2.6.0")
//    implementation("androidx.room:room-ktx:2.6.0")
//    // Coil
//    implementation("io.coil-kt:coil-compose:2.5.0")
//    // Firebase
//    implementation("com.google.firebase:firebase-analytics")
//    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
//    //API
//    implementation("com.google.code.gson:gson:2.8.6")
//
//}
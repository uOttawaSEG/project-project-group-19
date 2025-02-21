plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.eams"
    compileSdk = 34
    packaging {
        resources {
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/LICENSE.md"  // If you have LICENSE conflicts as well
        }
    }
    buildFeatures{
        dataBinding = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.eams"
        minSdk = 27
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    sourceSets {
        getByName("main") {
            res {
                srcDirs("src\\main\\res", "src\\main\\res\\2",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\admin", "src\\main\\res", "src\\main\\res\\layouts\\organizer",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\attendee"
                )
            }
        }
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.recyclerview)
    implementation(libs.firebase.ui.database)
    implementation(libs.google.firebase.auth)
    implementation(libs.junit.junit)
    implementation(libs.ext.junit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("com.sun.mail:android-activation:1.6.7")
}